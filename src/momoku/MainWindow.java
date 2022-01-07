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

import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Main window.
 */
public class MainWindow extends CenteredWindow {
    private int marginPaddingX;
    private int marginPaddingY;
    private int imageIndex;
    private ImageCanvas canvas;
    private ActionListener nextImageListener;

    public MainWindow(float sx, float sy, boolean srel, int mpx, int mpy) {
        super(sx, sy, srel);
        marginPaddingX = mpx;
        marginPaddingY = mpy;
        canvas = new ImageCanvas();
        nextImage(false);

        nextImageListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nextImage();
            }
        };
    }

    public MainWindow(float sx, float sy, boolean srel) {
        this(sx, sy, srel, 100, 50);
    }

    public MainWindow(float sx, float sy) {
        this(sx, sy, false);
    }

    public MainWindow() {
        this(.8f, .8f, true);
    }

    @Override
    public void init() {
        setTitle("Mōmoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        header.setFont(GlobalSettings.DEFAULT_FONT.deriveFont(Font.BOLD, 50f));
        panel.add(header, BorderLayout.NORTH);

        // Footer
        JPanel footerPanel = new JPanel();
        FlowLayout footerLayout = new FlowLayout(FlowLayout.CENTER, 20, 10);
        footerPanel.setLayout(footerLayout);

        JLabel footerLabel = new JLabel("Who is this?", SwingConstants.CENTER);
        footerLabel.setFont(GlobalSettings.DEFAULT_FONT.deriveFont(30f));
        footerPanel.add(footerLabel);

        JButton nextButton = new JButton("Next");
        nextButton.setPreferredSize(new Dimension(120, 50));
        nextButton.addActionListener(nextImageListener);

        footerPanel.add(nextButton);
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
    }

    public void nextImage() {
        nextImage(true);
    }

    private int updateImageIndex() {
        int previousIndex;
        do {
            previousIndex = imageIndex;
            imageIndex = Math.abs(GlobalSettings.RANDOM.nextInt()) % GlobalSettings.IMAGE_FILES.length;
        } while (imageIndex == previousIndex);
        return imageIndex;
    }

    public void nextImage(boolean repaint) {
        canvas.setImagePath(GlobalSettings.IMAGE_FILES[updateImageIndex()].getAbsolutePath());
        if (repaint)
            canvas.repaint();
    }
}
