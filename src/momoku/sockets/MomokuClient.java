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
    private User connectedUser;

    private MomokuClient() {

    }

    public void connectToServer() throws IOException {
        if (socket != null)
            return;

        int port = 3000;
        System.out.println("Connecting on port " + port + "...");
        socket = new Socket("localhost", port);
        System.out.println("Success!");
        receiver = new DataInputStream(socket.getInputStream());
        sender = new DataOutputStream(socket.getOutputStream());
    }

    public void closeConnection() throws IOException {
        if (socket == null)
            return;

        sender.writeUTF("end");
        sender.flush();
        sender.close();
        receiver.close();
        socket.close();
        socket = null;
    }

    public User getConnectedUser() {
        return connectedUser;
    }

    public void setConnectedUser(User user) {
        connectedUser = user;
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
