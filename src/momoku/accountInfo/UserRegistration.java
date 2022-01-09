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
    private JButton registerButton;
    private JButton loginButton;

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

        //Vérifier que le username n'a pas deja été utilisé

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

        registerButton = new JButton("Register");
        registerButton.setActionCommand("register");
        registerButton.addActionListener(this);

        registerButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
        registerButton.setBounds(399, 447, 259, 74);
        contentPane.add(registerButton);

        loginButton = new JButton("Déjà un compte ? Cliquez ici :)");
        loginButton.setActionCommand("login");
        loginButton.addActionListener(this);

        loginButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
        loginButton.setBounds(399, 447, 259, 74);
        contentPane.add(loginButton);

        contentPane.setLayout(gl);

        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "register":
                String emailId = email.getText();
                String userName = username.getText();
                String password = new String(passwordField.getPassword());
                String msg = "" + userName;
                msg += " \n";

                try {
                    Connection connection = DriverManager.getConnection(url, "root", "root")

                    String query = "INSERT INTO `MomokuDB`.`Users`('" + userName + "','" +
                        password + "')";

                    Statement sta = connection.createStatement();
                    int x = sta.executeUpdate(query);
                    if (x == 0) {
                        JOptionPane.showMessageDialog(registerButton, "This account alredy exists");
                    } else {
                        JOptionPane.showMessageDialog(registerButton,
                            "Welcome, " + msg + "Your account has been sucessfully created");
                    }
                    connection.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            
            case "login":
                //link to login page
                break;
            }
        
    }
}

