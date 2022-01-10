package momoku.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class MultiServerManager extends Thread {
	
	Socket s;
	DataInputStream din;
	DataOutputStream dout;
	Server serv;
	
	public MultiServerManager(Socket socket, Server server)
	{
		super("MultiServerManager");
		this.s = socket;
		this.serv = server;
	}
	
	public void ServerOutClientIn(String OutText)
	{
		try {
			long ThreadID=this.getId();
			dout.writeUTF(OutText);
			dout.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void ServerOutAllClientIn(String OutText)
	{
		for(int i=0;i<serv.ListeConnexions.size();i++)
		{
			MultiServerManager Connection=serv.ListeConnexions.get(i);
			Connection.ServerOutClientIn(OutText);
		}
	}
	
	public void run()
	{

	}
}
