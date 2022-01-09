package momoku;

import momoku.components.Screen;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainMenuScreen extends Screen {
    public MainMenuScreen() {
        super();

        // Main layout
        BorderLayout mainLayout = new BorderLayout();
        mainLayout.setHgap(100);
        mainLayout.setVgap(100);

        setAlignmentX(Component.CENTER_ALIGNMENT);
        setAlignmentY(Component.CENTER_ALIGNMENT);
        setLayout(mainLayout);

        // Header
        JLabel header = new JLabel("Mōmoku - Blind Test for the Weeb!", SwingConstants.CENTER);
        header.setFont(GlobalSettings.HEADER_FONT);
        add(header, BorderLayout.NORTH);

        // Center
        JPanel centerPanel = new JPanel();
        GridLayout flowLayout = new GridLayout(3, 1);
        flowLayout.setHgap(80);
        flowLayout.setVgap(80);
        centerPanel.setLayout(flowLayout);
        centerPanel.setBorder(new EmptyBorder(50, 150, 100, 150));

        JButton practiceModeButton = new JButton("Practice Mode");
        practiceModeButton.setActionCommand("practiceMode");
        practiceModeButton.addActionListener(parentListener);
        practiceModeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        practiceModeButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        centerPanel.add(practiceModeButton);

        JButton multiplayerButton = new JButton("Multiplayer");
        multiplayerButton.setActionCommand("multiplayer");
        multiplayerButton.addActionListener(parentListener);
        multiplayerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        multiplayerButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        centerPanel.add(multiplayerButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setActionCommand("exit");
        exitButton.addActionListener(parentListener);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        centerPanel.add(exitButton);

        JButton registerButton = new JButton("Account Creation");
        registerButton.setActionCommand("registerMode");
        registerButton.addActionListener(parentListener);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        centerPanel.add(registerButton);

        add(centerPanel, BorderLayout.CENTER);
    }
}
