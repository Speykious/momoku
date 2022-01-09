package momoku.sockets;
import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.Runnable;

public class SocketManager implements Runnable {
    private ObjectOutputStream oos;
    private ObjectInputStream ois;


    public SocketManager(Socket socket) {
        try {
            this.oos = new ObjectOutputStream(socket.getOutputStream());
            this.ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("IO error");
        }
    }


    @Override
    public void run() {
        // TODO Auto-generated method stub
        
    }
}
