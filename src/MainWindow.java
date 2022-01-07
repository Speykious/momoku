import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Main window.
 */
public class MainWindow extends CenteredWindow {
    public MainWindow(float sx, float sy, boolean srel) {
        super(sx, sy, srel);
    }

    public MainWindow(float sx, float sy) {
        super(sx, sy, false);
    }

    public MainWindow() {
        super(.8f, .8f, true);
    }

    @Override
    public void init() {
        setTitle("Hello World (Java Swing)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Hello World!", SwingConstants.CENTER);
        label.setFont(GlobalSettings.DEFAULT_FONT);
        getContentPane().add(label);
    }
}
