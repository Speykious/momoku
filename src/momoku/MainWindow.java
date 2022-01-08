package momoku;

import momoku.components.CenteredWindow;
import momoku.components.ImageCanvas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;

import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Main window.
 */
public class MainWindow extends CenteredWindow {
    private int marginPaddingX;
    private int marginPaddingY;
    private int imageIndex;
    private ImageCanvas canvas;

    private JButton guessButton;
    private JTextField guessTextField;
    private JLabel guessPointsLabel;
    private ActionListener guessListener;

    private PracticeGameState state;

    public MainWindow(float sx, float sy, boolean srel, int mpx, int mpy, PracticeGameState pgs) {
        super(sx, sy, srel);
        marginPaddingX = mpx;
        marginPaddingY = mpy;
        canvas = new ImageCanvas();
        guessTextField = new JTextField(16);
        guessButton = new JButton("Guess");
        guessPointsLabel = new JLabel("0 pts");

        guessListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guess();
            }
        };

        state = pgs;
    }

    public MainWindow(float sx, float sy, boolean srel, PracticeGameState pgs) {
        this(sx, sy, srel, 100, 50, pgs);
    }

    public MainWindow(float sx, float sy, boolean srel) {
        this(sx, sy, srel, new PracticeGameState());
    }

    public MainWindow(float sx, float sy, PracticeGameState pgs) {
        this(sx, sy, false, pgs);
    }

    public MainWindow(float sx, float sy) {
        this(sx, sy, false);
    }

    public MainWindow(PracticeGameState pgs) {
        this(.8f, .8f, true, pgs);
    }

    public MainWindow() {
        this(new PracticeGameState());
    }

    @Override
    public void init() {
        setTitle("Mōmoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Fonts
        Font headerFont = GlobalSettings.DEFAULT_FONT.deriveFont(Font.BOLD, 50f);
        Font footerFont = GlobalSettings.DEFAULT_FONT.deriveFont(30f);

        // Main layout
        BorderLayout mainLayout = new BorderLayout();
        mainLayout.setHgap(20);
        mainLayout.setVgap(20);

        JPanel panel = new JPanel();
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setAlignmentY(Component.CENTER_ALIGNMENT);
        panel.setLayout(mainLayout);
        panel.setBorder(new EmptyBorder(
                marginPaddingY, marginPaddingX,
                marginPaddingY, marginPaddingX));

        // Header
        JLabel header = new JLabel("Mōmoku - Blind Test for the Weeb!", SwingConstants.CENTER);
        header.setFont(headerFont);
        panel.add(header, BorderLayout.NORTH);

        // Footer
        JPanel footerPanel = new JPanel();
        FlowLayout footerLayout = new FlowLayout(FlowLayout.CENTER, 20, 10);
        footerPanel.setLayout(footerLayout);

        JLabel footerLabel = new JLabel("Who is this?", SwingConstants.CENTER);
        footerLabel.setFont(footerFont);
        footerPanel.add(footerLabel);

        guessTextField.addActionListener(guessListener);
        footerPanel.add(guessTextField);

        guessButton.setPreferredSize(new Dimension(120, 50));
        guessButton.addActionListener(guessListener);
        footerPanel.add(guessButton);

        guessPointsLabel.setFont(footerFont);
        footerPanel.add(guessPointsLabel);

        panel.add(footerPanel, BorderLayout.SOUTH);

        // Canvas
        JPanel canvasPanel = new JPanel();
        canvasPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        canvasPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        canvasPanel.setBorder(BorderFactory.createLineBorder(Color.getHSBColor(0f, 0f, .5f), 1));
        canvasPanel.add(canvas);
        panel.add(canvasPanel, BorderLayout.CENTER);

        add(panel);

        pack();
        updateSize();
        updateInternalPosition();
        setResizable(false);

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
        canvas.setImagePath(GlobalSettings.IMAGE_FILES[updateImageIndex()].getAbsolutePath());
        canvas.repaint();
    }

    public void guess() {
        guessButton.setEnabled(false);
        guessTextField.setEnabled(false);

        if (GlobalSettings.RANDOM.nextBoolean()) {
            guessTextField.setText("nope");
        } else {
            guessTextField.setText("correct!");
            state.addPoint();
        }

        GlobalSettings.TIMER.schedule(new TimerTask() {
            @Override
            public void run() {
                guessPointsLabel.setText(state.getPoints() + " pts");

                nextImage();
                guessButton.setEnabled(true);
                guessTextField.setEnabled(true);
                guessTextField.requestFocus();
            }
        }, 1000);
    }
}
