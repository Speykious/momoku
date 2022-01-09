package momoku.practiceMode;

import momoku.GlobalSettings;
import momoku.components.ImageCanvas;
import momoku.components.Screen;
import momoku.database.models.Image;
import momoku.database.repositories.ImageRepository;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Main window.
 */
public class PracticeModeScreen extends Screen implements ActionListener {
    private ImageCanvas canvas;

    private JButton guessButton;
    private JTextField guessTextField;
    private JLabel guessPointsLabel;

    private PracticeModeGameState state;

    public PracticeModeScreen() {
        super();

        state = new PracticeModeGameState();

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

        JButton backButton = new JButton("Go Back");
        backButton.setPreferredSize(new Dimension(120, 50));
        backButton.setActionCommand("back");
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

        nextImage();
    }

    public void nextImage() {
        guessTextField.setText("");
        guessPointsLabel.setText(state.getPoints() + " pts");

        canvas.setImagePath(Path.of(
            GlobalSettings.IMAGE_DIRECTORY.getAbsolutePath(),
            state.updateImage().getFilename()).toString());
        canvas.repaint();
    }

    private boolean verifyImage(String filename, String guess) {
        try {
            Image imageData = ImageRepository.REPOSITORY.get(filename);

            System.err.println("Showing: " + imageData.getWhoisthis());
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

    private void guess() {
        guessButton.setEnabled(false);
        guessTextField.setEnabled(false);

        String filename = Path.of(canvas.getImagePath()).getFileName().toString();
        if (verifyImage(filename, guessTextField.getText())) {
            guessTextField.setText("correct!");
            state.addPoint();
        } else {
            guessTextField.setText("nope");
        }

        GlobalSettings.TIMER.schedule(new TimerTask() {
            @Override
            public void run() {
                nextImage();
                guessButton.setEnabled(true);
                guessTextField.setEnabled(true);
                guessTextField.requestFocus();
            }
        }, 1000);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "back":
                parentListener.actionPerformed(new ActionEvent(this, 727, "mainMenu"));
                state.reset();
                nextImage();
                break;
            case "guess":
                guess();
                break;
        }
    }
}
