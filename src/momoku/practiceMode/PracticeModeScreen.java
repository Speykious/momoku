package momoku.practiceMode;

import momoku.GlobalSettings;
import momoku.components.ImageCanvas;
import momoku.components.Screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private int imageIndex;
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

    private int updateImageIndex() {
        int previousIndex;
        do {
            previousIndex = imageIndex;
            imageIndex = Math.abs(GlobalSettings.RANDOM.nextInt()) % GlobalSettings.IMAGE_FILES.length;
        } while (imageIndex == previousIndex);
        return imageIndex;
    }

    public void nextImage() {
        guessTextField.setText("");
        guessPointsLabel.setText(state.getPoints() + " pts");
        canvas.setImagePath(GlobalSettings.IMAGE_FILES[updateImageIndex()].getAbsolutePath());
        canvas.repaint();
    }

    private void guess() {
        guessButton.setEnabled(false);
        guessTextField.setEnabled(false);

        // TODO: remove random boolean with actual guessing system (requires a
        // database).
        if (GlobalSettings.RANDOM.nextBoolean()) {
            guessTextField.setText("nope");
        } else {
            guessTextField.setText("correct!");
            state.addPoint();
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
