package momoku.components;

import java.awt.Dimension;

import javax.swing.JFrame;

public abstract class CenteredWindow extends JFrame implements Runnable {
    protected boolean resizable;

    protected CenteredWindow(boolean resizable) {
        this.resizable = resizable;
    }

    /** Create and set up the window. */
    public abstract void init();

    @Override
    public void run() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        init();
        pack();
        updateInternalPosition();
        setResizable(resizable);
        setVisible(true);
    }

    protected void updateInternalPosition() {
        Dimension screenSize = getToolkit().getScreenSize();
        Dimension size = getSize();
        setLocation((screenSize.width - size.width) / 2, (screenSize.height - size.height) / 2);
    }
}
