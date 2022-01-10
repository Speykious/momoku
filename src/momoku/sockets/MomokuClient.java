package momoku.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Date;

import momoku.database.models.User;

public class MomokuClient {
    public static final MomokuClient INSTANCE = new MomokuClient();

    private Socket socket;
    private DataInputStream receiver;
	private DataOutputStream sender;

    private MomokuClient() {
        try {
            socket = new Socket("localhost", 3000);
            receiver = new DataInputStream(socket.getInputStream());
            sender = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private User getUser(String username, String password) throws IOException {
        sender.writeUTF(username);
        sender.writeUTF(password);
        sender.flush();

        if (!receiver.readBoolean())
            return null;
        
        return new User(
            username,
            password,
            receiver.readInt(),
            new Date(receiver.readLong()));
    }
    
    public User connect(String username, String password) throws IOException {
        sender.writeUTF("connect");
        return getUser(username, password);
    }
    
    public User register(String username, String password) throws IOException {
        sender.writeUTF("register");
        return getUser(username, password);
    }
}