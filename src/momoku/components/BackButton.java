package momoku.components;

import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.event.ActionListener;

public class BackButton extends JButton {
    public BackButton(ActionListener listener) {
        super("Go Back");
        setPreferredSize(new Dimension(120, 50));
        setActionCommand("back");
        addActionListener(listener);
    }
}
