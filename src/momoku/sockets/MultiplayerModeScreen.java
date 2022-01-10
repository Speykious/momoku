package momoku.sockets;

import momoku.GlobalSettings;
import momoku.components.ImageCanvas;
import momoku.components.Screen;
import momoku.database.models.Image;
import momoku.database.repositories.ImageRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.Scanner;

public class MultiplayerModeScreen extends Screen implements ActionListener {
    private ImageCanvas canvas;

    private JButton guessButton;
    private JTextField guessTextField;
    private JLabel guessPointsLabel;
    MultiClients ClientThread;

    private User state;

    public MultiplayerModeScreen() {
        super();

        state = new User();

        Handler handler = new Handler();

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

        JButton backButton = new JButton("Exit Game");
        backButton.setPreferredSize(new Dimension(120, 50));
        backButton.setActionCommand("exit");
        backButton.addActionListener(this);

        headerPanel.add(backButton);

        JLabel header = new JLabel("Practice Mode", SwingConstants.CENTER);
        header.setFont(GlobalSettings.HEADER_FONT);
        headerPanel.add(header);

        add(headerPanel, BorderLayout.NORTH);

        // Footer
        JPanel footerPanel = new JPanel();
        FlowLayout footerLayout = new FlowLayout(FlowLayout.CENTER, 20, 10);
        footerPanel.setLayout(footerLayout);

        JLabel footerLabel = new JLabel("Who is this?", SwingConstants.CENTER);
        footerLabel.setFont(GlobalSettings.FOOTER_FONT);
        footerPanel.add(footerLabel);

        guessTextField = new JTextField(16);
        guessTextField.addActionListener(this);
        guessTextField.setActionCommand("guess");
        footerPanel.add(guessTextField);

        guessButton = new JButton("Guess");
        guessButton.setPreferredSize(new Dimension(120, 50));
        guessButton.setActionCommand("guess");
        guessButton.addActionListener(this);
        footerPanel.add(guessButton);

        guessPointsLabel = new JLabel("0 pts");
        guessPointsLabel.setFont(GlobalSettings.FOOTER_FONT);
        footerPanel.add(guessPointsLabel);

        add(footerPanel, BorderLayout.SOUTH);

        // Canvas
        JPanel canvasPanel = new JPanel();
        canvasPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        canvasPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        canvasPanel.setBorder(BorderFactory.createLineBorder(Color.getHSBColor(0f, 0f, .5f), 1));
        canvas = new ImageCanvas();
        canvasPanel.add(canvas);
        add(canvasPanel, BorderLayout.CENTER);

        // Set timer to slide images
		try {
			Socket s = new Socket("localhost",3000);
			ClientThread = new MultiClients(s,this);
			ClientThread.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void nextImage() {
        guessTextField.setText("");
        guessPointsLabel.setText(state.getScore() + " pts");
        canvas.setImagePath(GlobalSettings.IMAGE_FILES[state.updateImageIndex()].getAbsolutePath());
        canvas.repaint();
    }

    private boolean verifyImage(String filename, String guess) {
        try {
            Image imageData = ImageRepository.REPOSITORY.get(filename);

            List<String> keywords = Arrays.asList(imageData.getWhoisthis().toLowerCase().split(" "));
            for (String guessKeyword : Arrays.asList(guess.toLowerCase().split(" "))) {
                if (!keywords.contains(guessKeyword))
                    return false;
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return false;
        }
    }

    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    private void guess() {
        // incremente score si le joueur répond correctement le premier
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "guess":
                //guess();
                break;
            case "exit" :
                //close socket when game ends
        }
    }
}
