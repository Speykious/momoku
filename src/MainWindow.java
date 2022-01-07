
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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

    public MainWindow(float sx, float sy, boolean srel, int mpx, int mpy) {
        super(sx, sy, srel);
        setMarginPadding(mpx, mpy);
    }

    public MainWindow(float sx, float sy, boolean srel) {
        this(sx, sy, srel, 100, 50);
    }

    public MainWindow(float sx, float sy) {
        this(sx ,sy, false);
    }

    public MainWindow() {
        this(.8f, .8f, true);
    }

    @Override
    public void init() {
        setTitle("Hello World (Java Swing)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        
        JLabel header = new JLabel("Hello World!", SwingConstants.CENTER);
        header.setFont(GlobalSettings.DEFAULT_FONT.deriveFont(50f));
        panel.add(header, BorderLayout.NORTH);

        JLabel footer = new JLabel("Bye World!", SwingConstants.CENTER);
        footer.setFont(GlobalSettings.DEFAULT_FONT.deriveFont(50f));
        panel.add(footer, BorderLayout.SOUTH);


        JPanel canvasPanel = new JPanel();
        canvasPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        canvasPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        // BoxLayout canvasLayout = new BoxLayout(canvasPanel.getcontent, BoxLayout.Y_AXIS);
        // canvasPanel.setLayout(canvasLayout);
        canvasPanel.setBorder(BorderFactory.createLineBorder(Color.getHSBColor(0f, 0f, .5f), 1));
        ImageCanvas canvas = new ImageCanvas("some path here");
        canvasPanel.add(canvas);
        panel.add(canvasPanel, BorderLayout.CENTER);
        add(panel);
    }

    public int getMarginPaddingX() {
        return marginPaddingX;
    }

    public void setMarginPaddingX(int x) {
        marginPaddingX = x;
    }

    public int getMarginPaddingY() {
        return marginPaddingY;
    }

    public void setMarginPaddingY(int y) {
        marginPaddingY = y;
    }

    public void setMarginPadding(int value) {
        marginPaddingX = value;
        marginPaddingY = value;
    }

    public void setMarginPadding(int x, int y) {
        marginPaddingX = x;
        marginPaddingY = y;
    }
}
