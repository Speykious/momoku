package momoku.multiplayer;

import momoku.GlobalSettings;
import momoku.components.BackButton;
import momoku.components.Screen;
import momoku.database.models.Room;
import momoku.sockets.MomokuClient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class RoomSelectionScreen extends Screen implements ActionListener {
    private final JTextField roomNameTextField;
    private final JButton createRoomButton;
    private final JPanel roomsPanel;

    public RoomSelectionScreen() {
        super();

        // Main layout
        BorderLayout mainLayout = new BorderLayout();
        mainLayout.setHgap(20);
        mainLayout.setVgap(20);

        setAlignmentX(Component.CENTER_ALIGNMENT);
        setAlignmentY(Component.CENTER_ALIGNMENT);
        setLayout(mainLayout);

        // Header
        JPanel headerPanel = new JPanel();
        FlowLayout headerLayout = new FlowLayout(FlowLayout.CENTER, 100, 0);
        headerPanel.setLayout(headerLayout);

        JButton backButton = new BackButton(this);
        headerPanel.add(backButton);

        JLabel header = new JLabel("Multiplayer", SwingConstants.CENTER);
        header.setFont(GlobalSettings.HEADER_FONT);
        headerPanel.add(header);

        add(headerPanel, BorderLayout.NORTH);

        // Footer
        JPanel footerPanel = new JPanel();
        FlowLayout footerLayout = new FlowLayout(FlowLayout.CENTER, 20, 10);
        footerPanel.setLayout(footerLayout);

        JLabel footerLabel = new JLabel("Room name:", SwingConstants.CENTER);
        footerLabel.setFont(GlobalSettings.FOOTER_FONT);
        footerPanel.add(footerLabel);

        roomNameTextField = new JTextField(16);
        roomNameTextField.addActionListener(this);
        roomNameTextField.setActionCommand("createRoom");
        footerPanel.add(roomNameTextField);

        createRoomButton = new JButton("Create Room");
        createRoomButton.setPreferredSize(new Dimension(250, 50));
        createRoomButton.setActionCommand("createRoom");
        createRoomButton.addActionListener(this);
        footerPanel.add(createRoomButton);

        add(footerPanel, BorderLayout.SOUTH);

        // Scrollpane
        roomsPanel = new JPanel();
        roomsPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        GridLayout roomsLayout = new GridLayout(0, 1);
        roomsLayout.setVgap(10);
        roomsPanel.setLayout(roomsLayout);

        JScrollPane roomsScrollPane = new JScrollPane(roomsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        roomsScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        roomsScrollPane.setPreferredSize(new Dimension(800, 700));
        roomsScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        roomsScrollPane.setAlignmentY(Component.CENTER_ALIGNMENT);
        roomsScrollPane.setBorder(BorderFactory.createLineBorder(Color.getHSBColor(0f, 0f, .5f), 1));
        add(roomsScrollPane, BorderLayout.CENTER);
    }

    public String getCreateRoomName() {
        return roomNameTextField.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "back":
                parentListener.actionPerformed(new ActionEvent(this, 727, "mainMenu"));
                break;
            case "createRoom":
            case "joinRoom":
                parentListener.actionPerformed(e);
                break;
        }
    }

    @Override
    public void reset() {
        roomsPanel.removeAll();

        try {
            List<Room> rooms = MomokuClient.INSTANCE.getRooms();
            for (Room room : rooms)
                roomsPanel.add(new RoomPanel(this, room));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
