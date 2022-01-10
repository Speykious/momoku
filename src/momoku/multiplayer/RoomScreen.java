package momoku.multiplayer;

import momoku.GlobalSettings;
import momoku.components.BackButton;
import momoku.components.Screen;
import momoku.database.models.Room;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class RoomScreen extends Screen implements ActionListener {
    private final JButton readyButton;
    private final JButton playButton;
    private final JPanel usersPanel;
    private Room room;
    private JLabel header;
    private int readyUsers = 0;
    private JLabel readyLabel;

    private ConfigPanel passConfigPanel;
    private ConfigPanel roundsConfigPanel;

    public RoomScreen() {
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

        header = new JLabel("", SwingConstants.CENTER);
        header.setFont(GlobalSettings.HEADER_FONT);
        headerPanel.add(header);

        add(headerPanel, BorderLayout.NORTH);

        // Footer
        JPanel footerPanel = new JPanel();
        FlowLayout footerLayout = new FlowLayout(FlowLayout.CENTER, 40, 10);
        footerPanel.setLayout(footerLayout);

        readyLabel = new JLabel("", SwingConstants.CENTER);
        readyLabel.setFont(GlobalSettings.FOOTER_FONT);
        footerPanel.add(readyLabel);

        readyButton = new JButton("Ready");
        readyButton.setPreferredSize(new Dimension(250, 50));
        readyButton.setActionCommand("ready");
        readyButton.addActionListener(this);
        footerPanel.add(readyButton);

        playButton = new JButton("Play");
        playButton.setPreferredSize(new Dimension(250, 50));
        playButton.setActionCommand("play");
        playButton.addActionListener(this);
        footerPanel.add(playButton);

        add(footerPanel, BorderLayout.SOUTH);

        // Users Scrollpane
        usersPanel = new JPanel();
        usersPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        GridLayout usersLayout = new GridLayout(0, 1);
        usersLayout.setVgap(10);
        usersPanel.setLayout(usersLayout);

        JScrollPane usersScrollPane = new JScrollPane(usersPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        usersScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        usersScrollPane.setPreferredSize(new Dimension(300, 700));
        usersScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        usersScrollPane.setAlignmentY(Component.CENTER_ALIGNMENT);
        usersScrollPane.setBorder(BorderFactory.createLineBorder(Color.getHSBColor(0f, 0f, .5f), 1));
        add(usersScrollPane, BorderLayout.WEST);

        // Configuration grid
        JPanel configPanel = new JPanel();
        configPanel.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));
        GridLayout configLayout = new GridLayout(0, 1);
        configLayout.setVgap(10);
        configPanel.setLayout(configLayout);

        passConfigPanel = new ConfigPanel(this, "Pass");
        configPanel.add(passConfigPanel);

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
        decimalFormat.setGroupingUsed(false);
        JTextField roundsTextField = new JFormattedTextField(decimalFormat);
        roundsConfigPanel = new ConfigPanel(this, "Rounds", roundsTextField);
        configPanel.add(roundsConfigPanel);

        add(configPanel);
    }

    public int getReadyUsers() {
        return readyUsers;
    }

    public void setReadyUsers(int ready) {
        readyUsers = ready;
        readyLabel.setText("(" + ready + " / N Ready)");
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
        reset();
    }

    public String getPass() {
        return passConfigPanel.getText();
    }

    public int getRounds() {
        return (Integer)(roundsConfigPanel.getValue());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "updatePass":
                room.setPass(getPass());
                break;
            case "updateRounds":
                room.setRounds(getRounds());
                break;
            case "back":
                parentListener.actionPerformed(new ActionEvent(this, 727, "multiplayer"));
                break;
        }
    }

    @Override
    public void reset() {
        if (room == null)
            return;

        header.setText(room.getTitle());
    }
}
