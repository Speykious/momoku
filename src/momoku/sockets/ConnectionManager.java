package momoku.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnectionManager extends Thread {
	Socket socket;
	Server server;
	DataInputStream din;
	DataOutputStream dout;

	public ConnectionManager(Socket socket, Server server) {
		super("MultiServerManager");
		this.socket = socket;
		this.server = server;
	}

	public void send(String text) throws IOException {
		dout.writeUTF(text);
		dout.flush();
	}

	public void run() {

	}
}
