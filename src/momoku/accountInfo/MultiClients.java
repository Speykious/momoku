package momoku.accountInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MultiClients extends Thread{
    Socket s;
	DataInputStream din;
	DataOutputStream dout;
	public ClientData c;
	public MainWindow GUI;
	
	public MultiClients(Socket OurMultiSocket, MainWindow gui)
	{
		s = OurMultiSocket;
		c = new ClientData();
		GUI = gui;
	}
	public void run()
	{
		try {
			din = new DataInputStream(s.getInputStream());
			dout = new DataOutputStream(s.getOutputStream());
			while(true)
			{
                //
            }

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				din.close();
				dout.close();
				s.close();
			} catch (IOException x) {
				// TODO Auto-generated catch block
				x.printStackTrace();
			}
		}
	}
	public void ClientData()
	{

	}
}
