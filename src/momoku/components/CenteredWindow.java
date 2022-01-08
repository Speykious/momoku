package momoku.components;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class CenteredWindow extends JFrame implements Runnable, ActionListener {
    protected CenteredWindow parent;
    protected JPanel mainPanel;
    private boolean visible;

    // Absolute Size coordinate values are between 0 and width/height
    // Relative Size coordinate values are between 0 and 1
    private float sizeX;
    private float sizeY;
    private boolean isSizeRelative;

    protected CenteredWindow(boolean visible, float sx, float sy, boolean srel) {
        this.visible = visible;
        sizeX = sx;
        sizeY = sy;
        isSizeRelative = srel;
    }

    protected CenteredWindow(boolean visible, float sx, float sy) {
        this(visible, sx, sy, false);
    }

    protected CenteredWindow(boolean visible) {
        this(visible, .8f, .8f, true);
    }

    protected CenteredWindow() {
        this(false);
    }

    /** Create and set up the window. */
    public abstract void init();

    @Override
    public void run() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        updateInternalSize();
        updateInternalPosition();
        init();
        setVisible(visible);
    }

    // Size methods
    protected void updateInternalSize() {
        setSize(getAbsoluteSize());
    }

    protected void updateSize() {
        Dimension size = getSize();
        float sx, sy;
        if (isSizeRelative) {
            Dimension screenSize = getToolkit().getScreenSize();
            sx = size.width / (float)screenSize.width;
            sy = size.height / (float)screenSize.height;
        } else {
            sx = (float)size.width;
            sy = (float)size.height;
        }
        setSize(sx, sy);
    }

    protected void updateInternalPosition() {
        Dimension screenSize = getToolkit().getScreenSize();
        Dimension size = getAbsoluteSize();
        setLocation((screenSize.width - size.width) / 2, (screenSize.height - size.height) / 2);
    }

    public Dimension getAbsoluteSize() {
        float sx, sy;
        if (isSizeRelative) {
            Dimension screenSize = getToolkit().getScreenSize();
            sx = screenSize.width * sizeX;
            sy = screenSize.height * sizeY;
        } else {
            sx = sizeX;
            sy = sizeY;
        }

        return new Dimension((int)sx, (int)sy);
    }

    public float getSizeX() {
        return sizeX;
    }

    public void setSizeX(float x) {
        sizeX = x;
        updateInternalSize();
    }

    public float getSizeY() {
        return sizeY;
    }

    public void setSizeY(float y) {
        sizeY = y;
        updateInternalSize();
    }

    public void setSize(float x, float y) {
        sizeX = x;
        sizeY = y;
        updateInternalSize();
    }

    public boolean isSizeRelative() {
        return isSizeRelative;
    }

    public void setSizeRelative(boolean value) {
        isSizeRelative = value;
        updateInternalSize();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public CenteredWindow getParent() {
        return parent;
    }

    public void setParent(CenteredWindow parent) {
        this.parent = parent;
    }
}
