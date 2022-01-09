package momoku.sockets;

import java.util.concurrent.LinkedBlockingQueue;

public class Client {
    private SocketManager sm;
    private String user;
    private Thread thread;
    private String[] blockedChannels;

    private static final Client client = new Client();
    private String channel;
 //Creer un payload
    public void sendConnection(String user, String avatar) {
        this.user = user;
        Payload payload = new Payload(Payload.Type.CONNECTION);
        payload.addProperty("user", user);
        sm.send(payload);
    }
    
}
