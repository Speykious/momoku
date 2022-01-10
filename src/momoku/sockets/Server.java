package momoku.sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
	public static final Server INSTANCE = new Server();
	private ServerSocket serverSocket;
	List<ConnectionManager> connections = new ArrayList<ConnectionManager>();

	public static void main(String[] args) {
		INSTANCE.listen();
	}

	private Server() {
		try {
			serverSocket = new ServerSocket(3000);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void listen() {
		try {
			while (true) {
				Socket socket = serverSocket.accept();
				ConnectionManager connection = new ConnectionManager(socket, this);
				connection.start();
				connections.add(connection);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendAll(String text) throws IOException {
		for (ConnectionManager manager : connections)
			manager.send(text);
	}
}
