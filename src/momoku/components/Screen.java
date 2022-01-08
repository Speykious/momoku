package momoku.components;

import javax.swing.JPanel;

import java.awt.event.ActionListener;

public abstract class Screen extends JPanel {
    protected ActionListener parentListener;

    protected Screen(ActionListener listener) {
        parentListener = listener;
    }

    protected Screen() {
        parentListener = momoku.MainWindow.WINDOW;
    }
}
