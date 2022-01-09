package momoku.sockets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService; 
import java.util.concurrent.Executors;
import java.util.Timer;

public class Server implements Runnable {
    private final List<SocketManager> sockets;
    private final ExecutorService pool;
    private final Map<SocketManager, User> activeUsers;
    private final Map<String, Integer> questions;
    private final Map<String, Timer> timers;

    final int POINTS = 10;
    final int SECONDSTIMEOUT = 45;

    public Server() {
        this.sockets = new ArrayList<SocketManager>();
        this.activeUsers = new HashMap<SocketManager, User>();
        this.pool = Executors.newCachedThreadPool();
        this.questions = new HashMap<String, Integer>();
        this.timers = new HashMap<String, Timer>();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        
    }
}
