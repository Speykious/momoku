package momoku.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

import momoku.database.models.User;
import momoku.database.repositories.UserRepository;

public class ConnectionManager extends Thread {
	private final Socket clientSocket;
	private final DataInputStream receiver;
	private final DataOutputStream sender;

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
				executeCommand(command);
			} catch (EOFException e) {
				System.err.println("FATAL: client ended connection unexpectedly");
				break;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void executeCommand(String command) throws IOException, SQLException {
		switch (command) {
			case "connect" -> connect();
			case "register" -> register();
			default -> System.err.println("Command '" + command + "' does not exist");
		}
	}

	private void sendUserInfo(User user) throws IOException {
		sender.writeInt(user.getGamesWon());
		sender.writeLong(user.getCreationDate().getTime());
	}

	private void connect() throws IOException, SQLException {
        String username = receiver.readUTF();
        String password = receiver.readUTF();

		User user = UserRepository.REPOSITORY.get(username);
		if (user == null || user.getPassword() != password) {
			sender.writeBoolean(false);
		} else {
			sender.writeBoolean(true);
			sendUserInfo(user);
		}

		sender.flush();
	}

	private void register() throws IOException, SQLException {
        String username = receiver.readUTF();
        String password = receiver.readUTF();

		if (UserRepository.REPOSITORY.get(username) != null) {
			sender.writeBoolean(false);
		} else {
			User user = new User(username, password);
			UserRepository.REPOSITORY.save(user);
			sendUserInfo(user);
		}

		sender.flush();
	}
}
