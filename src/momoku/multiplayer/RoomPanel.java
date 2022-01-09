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
import javax.swing.SwingConstants;

import momoku.GlobalSettings;
import momoku.components.Screen;
import momoku.database.models.Room;

public class RoomPanel extends Screen implements ActionListener {
    private Room room;

    public RoomPanel(RoomSelectionScreen screen, Room room) {
        super(screen);
        this.room = room;

        FlowLayout layout = new FlowLayout(FlowLayout.RIGHT, 20, 20);
        setLayout(layout);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setAlignmentY(Component.CENTER_ALIGNMENT);
        setBorder(BorderFactory.createLineBorder(Color.getHSBColor(.1f, .5f, .5f), 1));

        JLabel footerLabel = new JLabel(room.getTitle(), SwingConstants.CENTER);
        footerLabel.setFont(GlobalSettings.FOOTER_FONT);
        add(footerLabel);

        JTextField roomNameTextField = new JTextField(16);
        roomNameTextField.addActionListener(this);
        roomNameTextField.setActionCommand("joinRoom");
        add(roomNameTextField);

        JButton createRoomButton = new JButton("Join");
        createRoomButton.setPreferredSize(new Dimension(120, 50));
        createRoomButton.setActionCommand("joinRoom");
        createRoomButton.addActionListener(this);
        add(createRoomButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "joinRoom":
                parentListener.actionPerformed(new ActionEvent(this, room.getId(), "room"));
                break;
        }
    }
}
