package momoku;

import momoku.components.Screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import javax.swing.JPasswordField;

public class LoginScreen extends Screen implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;

    public LoginScreen() {
        super();

        // Main layout
        BorderLayout mainLayout = new BorderLayout();
        mainLayout.setHgap(100);
        mainLayout.setVgap(100);

        setAlignmentX(Component.CENTER_ALIGNMENT);
        setAlignmentY(Component.CENTER_ALIGNMENT);
        setLayout(mainLayout);

        // Header
        JPanel headerPanel = new JPanel();
        FlowLayout headerLayout = new FlowLayout(FlowLayout.CENTER, 100, 0);
        headerPanel.setLayout(headerLayout);

        JButton backButton = new JButton("Go Back");
        backButton.setPreferredSize(new Dimension(120, 50));
        backButton.setActionCommand("back");
        backButton.addActionListener(this);
        headerPanel.add(backButton);

        JLabel header = new JLabel("Log in / Sign in", SwingConstants.CENTER);
        header.setFont(GlobalSettings.HEADER_FONT);
        headerPanel.add(header);

        add(headerPanel, BorderLayout.NORTH);

        // Center
        JPanel formPanel = new JPanel();
        GridLayout formLayout = new GridLayout(0, 2);
        formLayout.setHgap(20);
        formLayout.setVgap(30);
        formPanel.setLayout(formLayout);
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        Border formBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.getHSBColor(0f, 0f, .5f), 1),
            new EmptyBorder(100, 150, 100, 150));
        formPanel.setBorder(formBorder);

        JLabel usernameLabel = new JLabel("Username", SwingConstants.CENTER);
        formPanel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setActionCommand("clearMessage");
        usernameField.addActionListener(this);
        formPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password", SwingConstants.CENTER);
        formPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setActionCommand("clearMessage");
        passwordField.addActionListener(this);
        formPanel.add(passwordField);

        formPanel.add(new Box(BoxLayout.X_AXIS));
        formPanel.add(new Box(BoxLayout.X_AXIS));

        JButton loginButton = new JButton("Log in");
        loginButton.setActionCommand("login");
        loginButton.addActionListener(this);
        formPanel.add(loginButton);

        JButton signinButton = new JButton("Sign in");
        signinButton.setActionCommand("register");
        signinButton.addActionListener(this);
        formPanel.add(signinButton);

        add(formPanel, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel();
        FlowLayout footerLayout = new FlowLayout(FlowLayout.CENTER, 100, 0);
        footerPanel.setLayout(footerLayout);

        JButton exitButton = new JButton("Exit");
        exitButton.setActionCommand("exit");
        exitButton.addActionListener(parentListener);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        footerPanel.add(exitButton);

        messageLabel = new JLabel(" ", SwingConstants.CENTER);
        messageLabel.setForeground(Color.getHSBColor(0f, .7f, .8f));
        footerPanel.add(messageLabel);
        
        add(footerPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "clearMessage":
                messageLabel.setText(" ");
                break;
            case "login":
                messageLabel.setText("Will login shortly");
                break;
            case "register":
                messageLabel.setText("Will register shortly");
                break;
        }
    }
}