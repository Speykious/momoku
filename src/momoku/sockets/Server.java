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
			int port = 3000;
			System.out.println("Connecting to port " + port + "...");
			serverSocket = new ServerSocket(port);
			System.out.println("Success");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void listen() {
		try {
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("Client " + socket.getInetAddress() + " has connected");
				ConnectionManager connection = new ConnectionManager(socket);
				connection.start();
				connections.add(connection);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
