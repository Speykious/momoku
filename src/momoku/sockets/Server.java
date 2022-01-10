package momoku.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	ServerSocket ss;
	ArrayList<MultiServerManager> ListeConnexions =new ArrayList<MultiServerManager>();
	
	public static void main(String[] args) {
		new Server();

	}
	public Server() {
		try {
			ss = new ServerSocket(3000); //Connexion au port 3000
			while(true)
			{
				Socket socket = ss.accept(); //Connexions au port acceptées
				MultiServerManager connexion = new MultiServerManager(socket, this);
				connexion.start(); //Thread
				ListeConnexions.add(connexion);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
