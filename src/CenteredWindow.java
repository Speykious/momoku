import java.awt.Dimension;

import javax.swing.JFrame;

public abstract class CenteredWindow extends JFrame implements Runnable {
    // Absolute Size coordinate values are between 0 and width/height
    // Relative Size coordinate values are between 0 and 1
    private float sizeX;
    private float sizeY;
    private boolean isSizeRelative;

    protected CenteredWindow(float sx, float sy, boolean srel) {
        sizeX = sx;
        sizeY = sy;
        isSizeRelative = srel;
    }

    protected CenteredWindow(float sx, float sy) {
        this(sx, sy, false);
    }

    protected CenteredWindow() {
        this(.8f, .8f, true);
    }

    /** Create and set up the window. */
    public abstract void init();

    @Override
    public void run() {
        updateSize();
        updatePosition();
        init();
        setVisible(true);
    }

    // Size methods

    private void updateSize() {
        setSize(getAbsoluteSize());
    }

    private void updatePosition() {
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
        updateSize();
    }

    public float getSizeY() {
        return sizeY;
    }

    public void setSizeY(float y) {
        sizeY = y;
        updateSize();
    }

    public void setSize(float x, float y) {
        sizeX = x;
        sizeY = y;
    }

    public boolean isSizeRelative() {
        return isSizeRelative;
    }

    public void setSizeRelative(boolean value) {
        isSizeRelative = value;
        updateSize();
    }
}
