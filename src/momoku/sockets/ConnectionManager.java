package momoku.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

import momoku.database.models.Image;
import momoku.database.models.Room;
import momoku.database.models.User;
import momoku.database.repositories.ImageRepository;
import momoku.database.repositories.RoomRepository;
import momoku.database.repositories.UserRepository;

public class ConnectionManager extends Thread {
	private final Socket clientSocket;
	private final DataInputStream receiver;
	private final DataOutputStream sender;
	private User connectedUser;

	public ConnectionManager(Socket socket) throws IOException {
		super("ConnectionManager");
		clientSocket = socket;
		receiver = new DataInputStream(clientSocket.getInputStream());
		sender = new DataOutputStream(clientSocket.getOutputStream());
	}

	@Override
	public void run() {
		while (clientSocket.isConnected()) {
			try {
				String command = receiver.readUTF();
				System.out.println("Command received: " + command);
				if (command.equals("end"))
					break;
				executeCommand(command);
			} catch (EOFException e) {
				System.err.println("FATAL: client ended connection unexpectedly");
				break;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("Connection with client " + clientSocket.getInetAddress() + " ended");
		MomokuServer.INSTANCE.endConnection(this);
	}

	private void executeCommand(String command) throws IOException, SQLException {
		switch (command) {
			case "connect" -> connect();
			case "register" -> register();
			case "getRandomImage" -> getRandomImage();
			case "createRoom" -> createRoom();
			case "getRooms" -> getRooms();
			case "joinRoom" -> joinRoom();
			case "leaveRoom" -> leaveRoom();
			default -> System.err.println("Command '" + command + "' does not exist");
		}
	}

    private void sendUserInfo(User user) throws IOException {
		sender.writeInt(user.getGamesWon());
		sender.writeLong(user.getCreationDate().getTime());
	}

	private void connect() throws IOException, SQLException {
		System.out.println("Connecting a user");
		String username = receiver.readUTF();
		String password = receiver.readUTF();

		User user = UserRepository.REPOSITORY.get(username);
		if (user == null || !user.getPassword().equals(password)) {
			System.out.println("Invalid credentials");
			sender.writeBoolean(false);
		} else {
			System.out.println("User " + username + " connected successfully");
			connectedUser = user;
			sender.writeBoolean(true);
			sendUserInfo(user);
		}

		
		sender.flush();
	}

	private void register() throws IOException, SQLException {
		System.out.println("Registering a user");
		String username = receiver.readUTF();
		String password = receiver.readUTF();

		if (UserRepository.REPOSITORY.get(username) != null) {
			System.out.println("User " + username + " already exists");
			sender.writeBoolean(false);
		} else {
			System.out.println("Registered new user " + username);
			User user = new User(username, password);
			UserRepository.REPOSITORY.save(user);
			connectedUser = user;
			sendUserInfo(user);
		}

		sender.flush();
	}

	private void getRandomImage() throws IOException, SQLException {
		String filename = receiver.readUTF();
		Image image;
		List<Image> images = ImageRepository.REPOSITORY.getRandoms(2);
		if (filename.equals("") || !filename.equals(images.get(0).getFilename()))
			image = images.get(0);
		else
			image = images.get(1);
		
		System.out.println("Sending " + image.getFilename());
		sender.writeUTF(image.getFilename());
		sender.writeUTF(image.getWhoisthis());

		sender.flush();
	}

	private void createRoom() throws IOException, SQLException {
		String title = receiver.readUTF();
		
		User owner = connectedUser;
		int roomCount = RoomRepository.REPOSITORY.count();
		Room room = new Room(roomCount + 1, title, owner);
		RoomRepository.REPOSITORY.save(room);
		owner.setCurrentRoom(room);
		UserRepository.REPOSITORY.update(owner);

		sender.writeInt(room.getId());
		sender.writeInt(room.getRounds());
		sender.writeLong(room.getCreationDate().getTime());

		sender.flush();
	}

	private void getRooms() throws IOException, SQLException {
		List<Room> rooms = RoomRepository.REPOSITORY.list();

		sender.writeInt(rooms.size());
		for (Room room : rooms) {
			sender.writeInt(room.getId());
			sender.writeUTF(room.getTitle());

			User owner = room.getOwner();
			sender.writeUTF(owner.getUsername());
			sender.writeBoolean(owner.isPlaying());
			sender.writeBoolean(owner.isReady());
			sender.writeInt(owner.getGamesWon());
			sender.writeInt(owner.getCurrentScore());
			sender.writeLong(owner.getCreationDate().getTime());

			sender.writeInt(room.getRounds());
			sender.writeLong(room.getCreationDate().getTime());
		}
	}

	private void joinRoom() throws IOException, SQLException {
		int roomId = receiver.readInt();
		String pass = receiver.readUTF();

		Room room = RoomRepository.REPOSITORY.get(roomId);
		if (room.getPass() != null && !room.getPass().equals(pass)) {
			sender.writeBoolean(false);
		} else {
			connectedUser.setCurrentRoom(room);
			UserRepository.REPOSITORY.update(connectedUser);
			sender.writeBoolean(true);
		}

		sender.flush();
	}

	private void leaveRoom() throws IOException, SQLException {
		Room room = connectedUser.getCurrentRoom();
		connectedUser.setCurrentRoom(null);
		UserRepository.REPOSITORY.update(connectedUser);
		
		List<User> usersInRoom = UserRepository.REPOSITORY.getUsersInRoom(room);
		if (usersInRoom.isEmpty()) {
			RoomRepository.REPOSITORY.delete(room);
		} else {
			room.setOwner(usersInRoom.get(0));
		}
	}
}
