package momoku.accountInfo;

import momoku.components.Screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.BorderFactory;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;


public class UserRegistration extends Screen implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JTextField email;
    private JTextField username;
    private JPasswordField passwordField;
    private JButton btnNewButton;

    public UserRegistration() {
        GridLayout gl = new GridLayout(8, 1);

        JPanel contentPane = new JPanel();
        contentPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.setAlignmentY(Component.CENTER_ALIGNMENT);
        contentPane.setBorder(BorderFactory.createLineBorder(Color.getHSBColor(0f, 0f, .5f), 1));
        add(contentPane, BorderLayout.CENTER);

        JLabel lblNewUserRegister = new JLabel("Register Form");
        lblNewUserRegister.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        lblNewUserRegister.setBounds(10, 20, 80, 25);
        contentPane.add(lblNewUserRegister);

        JLabel lblEmailAddress = new JLabel("Email\r\n address");
        lblEmailAddress.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblEmailAddress.setBounds(10, 20, 80, 25);
        contentPane.add(lblEmailAddress);

        email = new JTextField();
        email.setFont(new Font("Tahoma", Font.PLAIN, 32));
        email.setBounds(100, 20, 165, 25);
        contentPane.add(email);
        email.setColumns(10);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblUsername.setBounds(10, 20, 80, 25);
        contentPane.add(lblUsername);

        username = new JTextField();
        username.setFont(new Font("Tahoma", Font.PLAIN, 32));
        username.setBounds(100, 20, 165, 25);
        contentPane.add(username);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblPassword.setBounds(10, 20, 80, 25);
        contentPane.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        passwordField.setBounds(100, 20, 165, 25);
        contentPane.add(passwordField);

        btnNewButton = new JButton("Register");
        btnNewButton.setActionCommand("Register");
        btnNewButton.addActionListener(this);

        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
        btnNewButton.setBounds(399, 447, 259, 74);
        contentPane.add(btnNewButton);

        contentPane.setLayout(gl);

        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Register":
                String emailId = email.getText();
                String userName = username.getText();
                String password = new String(passwordField.getPassword());
                String msg = "" + userName;
                msg += " \n";

                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/swing_demo", "root", "root");

                    String query = "INSERT INTO account values('" + userName + "','" +
                        password + "','" + emailId + "')";

                    Statement sta = connection.createStatement();
                    int x = sta.executeUpdate(query);
                    if (x == 0) {
                        JOptionPane.showMessageDialog(btnNewButton, "This account alredy exists");
                    } else {
                        JOptionPane.showMessageDialog(btnNewButton,
                            "Welcome, " + msg + "Your account has been sucessfully created");
                    }
                    connection.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            }
        
    }
}

