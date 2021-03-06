package momoku.practiceMode;

import momoku.GlobalSettings;
import momoku.components.ImageCanvas;
import momoku.components.Screen;
import momoku.database.models.Image;
import momoku.sockets.MomokuClient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Path;
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

    private Image image;
    private int points;

    public PracticeModeScreen() {
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
    }

    public void updateImage(Image image) {
        this.image = image;

        guessTextField.setText("");
        guessPointsLabel.setText(points + " pts");

        canvas.setImagePath(Path.of(
                GlobalSettings.IMAGE_DIRECTORY.getAbsolutePath(),
                image.getFilename()).toString());
        canvas.repaint();
    }

    public void updateImage() {
        try {
            image = MomokuClient.INSTANCE.getRandomImage(image);
        } catch (IOException e) {
            e.printStackTrace();
            image = null;
        }
        updateImage(image);
    }

    private boolean verifyImage(String filename, String guess) {
        System.err.println("Showing: " + image.getWhoisthis());
        List<String> keywords = Arrays.asList(image.getWhoisthis().toLowerCase().split(" "));
        for (String guessKeyword : Arrays.asList(guess.toLowerCase().split(" "))) {
            if (!keywords.contains(guessKeyword))
                return false;
        }

        return true;

    }

    protected void guess() {
        guessButton.setEnabled(false);
        guessTextField.setEnabled(false);

        String filename = Path.of(canvas.getImagePath()).getFileName().toString();
        if (verifyImage(filename, guessTextField.getText())) {
            guessTextField.setText("correct!");
            points++;
        } else {
            guessTextField.setText("nope");
        }

        GlobalSettings.TIMER.schedule(new TimerTask() {
            @Override
            public void run() {
                updateImage();
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
                break;
            case "guess":
                guess();
                break;
        }
    }

    @Override
    public void reset() {
        points = 0;
        updateImage();
    }
}
