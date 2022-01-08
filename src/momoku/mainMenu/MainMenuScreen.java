package momoku.mainMenu;

import momoku.GlobalSettings;
import momoku.components.Screen;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
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
        flowLayout.setHgap(40);
        flowLayout.setVgap(40);
        centerPanel.setLayout(flowLayout);
        centerPanel.setBorder(new EmptyBorder(50, 150, 100, 150));

        Dimension buttonSize = new Dimension(300, 100);

        JButton practiceModeButton = new JButton("Practice Mode");
        practiceModeButton.setActionCommand("practiceMode");
        practiceModeButton.addActionListener(parentListener);
        practiceModeButton.setPreferredSize(buttonSize);
        practiceModeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        practiceModeButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        centerPanel.add(practiceModeButton);

        JButton multiplayerButton = new JButton("Multiplayer");
        multiplayerButton.setActionCommand("multiplayer");
        multiplayerButton.addActionListener(parentListener);
        multiplayerButton.setPreferredSize(buttonSize);
        multiplayerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        multiplayerButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        centerPanel.add(multiplayerButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setActionCommand("exit");
        exitButton.addActionListener(parentListener);
        exitButton.setPreferredSize(buttonSize);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        centerPanel.add(exitButton);

        add(centerPanel, BorderLayout.CENTER);
    }
}
