package momoku.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

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
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void executeCommand(String command) {
		switch (command) {
			default -> System.err.println("Command '" + command + "' does not exist");
		}
	}
}
