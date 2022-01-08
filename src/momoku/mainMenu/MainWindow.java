package momoku.mainMenu;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import momoku.GlobalSettings;
import momoku.components.CenteredWindow;

public class MainWindow extends CenteredWindow {
    private int marginPaddingX;
    private int marginPaddingY;

    public MainWindow(boolean visible) {
        super(visible, .8f, .8f, true);
        marginPaddingX = 100;
        marginPaddingY = 50;
    }

    public MainWindow() {
        this(false);
    }

    @Override
    public void init() {
        Dimension buttonSize = new Dimension(300, 100);

        // Main layout
        BorderLayout mainLayout = new BorderLayout();
        mainLayout.setHgap(100);
        mainLayout.setVgap(100);

        mainPanel = new JPanel();
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        mainPanel.setLayout(mainLayout);
        mainPanel.setBorder(new EmptyBorder(
                marginPaddingY, marginPaddingX,
                marginPaddingY, marginPaddingX));

        // Header
        JLabel header = new JLabel("Mōmoku - Blind Test for the Weeb!", SwingConstants.CENTER);
        header.setFont(GlobalSettings.HEADER_FONT);
        mainPanel.add(header, BorderLayout.NORTH);

        // Center
        JPanel centerPanel = new JPanel();
        GridLayout flowLayout = new GridLayout(3, 1);
        flowLayout.setHgap(40);
        flowLayout.setVgap(40);
        centerPanel.setLayout(flowLayout);
        centerPanel.setBorder(new EmptyBorder(50, 150, 100, 150));

        JButton practiceModeButton = new JButton("Practice Mode");
        practiceModeButton.setActionCommand("practiceMode");
        practiceModeButton.addActionListener(parent);
        practiceModeButton.setPreferredSize(buttonSize);
        practiceModeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        practiceModeButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        centerPanel.add(practiceModeButton);

        JButton multiplayerButton = new JButton("Multiplayer");
        multiplayerButton.setActionCommand("multiplayer");
        multiplayerButton.addActionListener(parent);
        multiplayerButton.setPreferredSize(buttonSize);
        multiplayerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        multiplayerButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        centerPanel.add(multiplayerButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setActionCommand("exit");
        exitButton.addActionListener(parent);
        exitButton.setPreferredSize(buttonSize);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        centerPanel.add(exitButton);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
