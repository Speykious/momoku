package momoku.multiplayer;

import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import momoku.GlobalSettings;
import momoku.components.Screen;
import momoku.database.models.Room;

public class RoomPanel extends Screen implements ActionListener {
    private final Room room;
    private final JLabel footerLabel;
    private final JTextField roomPasswordField;

    public RoomPanel(RoomSelectionScreen screen, Room room) {
        super(screen);
        this.room = room;

        FlowLayout layout = new FlowLayout(FlowLayout.RIGHT, 30, 20);
        setLayout(layout);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setAlignmentY(Component.CENTER_ALIGNMENT);
        setBorder(BorderFactory.createLineBorder(Color.getHSBColor(.1f, .5f, .5f), 1));

        footerLabel = new JLabel("", SwingConstants.CENTER);
        footerLabel.setFont(GlobalSettings.FOOTER_FONT);
        add(footerLabel);

        roomPasswordField = new JPasswordField(10);
        roomPasswordField.addActionListener(this);
        roomPasswordField.setActionCommand("joinRoom");
        add(roomPasswordField);

        JButton createRoomButton = new JButton("Join");
        createRoomButton.setPreferredSize(new Dimension(120, 50));
        createRoomButton.setActionCommand("joinRoom");
        createRoomButton.addActionListener(this);
        add(createRoomButton);

        reset();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "joinRoom":
                parentListener.actionPerformed(new ActionEvent(this, room.getId(), "joinRoom"));
                break;
        }
    }

    @Override
    public void reset() {
        footerLabel.setText("#" + room.getId() + " - " + room.getTitle());
        roomPasswordField.setText("");
        if (room.getPass() == null)
            roomPasswordField.setEnabled(false);
    }

    public Room getRoom() {
        return room;
    }

    public String getEnteredPassword() {
        return roomPasswordField.getText();
    }
}
