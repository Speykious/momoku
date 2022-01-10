package momoku;

import momoku.components.CenteredWindow;
import momoku.components.Screen;
import momoku.database.models.Room;
import momoku.multiplayer.RoomPanel;
import momoku.multiplayer.RoomScreen;
import momoku.multiplayer.RoomSelectionScreen;
import momoku.practiceMode.PracticeModeScreen;
import momoku.sockets.MomokuClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.awt.CardLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public final class MainWindow extends CenteredWindow implements ActionListener {
    public static final MainWindow WINDOW = new MainWindow();

    private JPanel cardPanel;
    private CardLayout cardLayout;
    private RoomScreen roomScreen;
    private RoomSelectionScreen roomSelectionScreen;

    private HashMap<String, Screen> cards;

    private MainWindow() {
        super(false);
        cards = new HashMap<String, Screen>();
    }

    @Override
    public void init() {
        setTitle("Mōmoku");

        cards.put("login", new LoginScreen());
        cards.put("mainMenu", new MainMenuScreen());
        cards.put("practiceMode", new PracticeModeScreen());
        roomSelectionScreen = new RoomSelectionScreen();
        roomScreen = new RoomScreen();
        cards.put("multiplayer", roomSelectionScreen);
        cards.put("room", roomScreen);

        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        cardPanel.setBorder(new EmptyBorder(50, 100, 50, 100));

        for (Entry<String, Screen> entry : cards.entrySet())
            cardPanel.add(entry.getValue(), entry.getKey());

        add(cardPanel);
        cardLayout.show(cardPanel, "login");
    }

    private void showScreen(String screenName) {
        cards.get(screenName).reset();
        cardLayout.show(cardPanel, screenName);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "exit":
                try {
                    MomokuClient.INSTANCE.closeConnection();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.exit(0);
                break;
            case "joinRoom":
                RoomPanel roomPanel = (RoomPanel)e.getSource();
                roomScreen.setRoom(roomPanel.getRoom());
                showScreen("room");
                break;
            case "createRoom":
                try {
                    String roomName = roomSelectionScreen.getCreateRoomName();
                    Room createdRoom = MomokuClient.INSTANCE.createRoom(roomName);
                    roomScreen.setRoom(createdRoom);
                    showScreen("room");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                break;
            default:
                showScreen(e.getActionCommand());
                break;
        }
    }
}
