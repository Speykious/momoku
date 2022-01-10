package momoku.multiplayer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import momoku.GlobalSettings;
import momoku.components.Screen;

public class ConfigPanel extends Screen implements ActionListener {
    private JTextField textField;
    private String title;
    private Object lastSetting;

    public ConfigPanel(ActionListener parent, String title, JTextField textField) {
        super(parent);
        this.title = title;

        setBorder(BorderFactory.createLineBorder(Color.getHSBColor(.1f, .5f, .5f), 1));

        FlowLayout layout = new FlowLayout(FlowLayout.RIGHT, 30, 20);
        setLayout(layout);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setAlignmentY(Component.CENTER_ALIGNMENT);

        JLabel roundsLabel = new JLabel(title, SwingConstants.CENTER);
        roundsLabel.setFont(GlobalSettings.FOOTER_FONT);
        add(roundsLabel);

        this.textField = textField;
        this.textField.setColumns(8);
        textField.addActionListener(this);
        textField.setActionCommand("update");
        add(textField);

        JButton updateRoundsButton = new JButton("Update");
        updateRoundsButton.setPreferredSize(new Dimension(150, 50));
        updateRoundsButton.setActionCommand("update");
        updateRoundsButton.addActionListener(this);
        add(updateRoundsButton);
    }

    public ConfigPanel(ActionListener parent, String title) {
        this(parent, title, new JTextField());
    }

    public String getText() {
        return (String)lastSetting;
    }

    public Object getValue() {
        return lastSetting;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "update":
                reset();
                parentListener.actionPerformed(new ActionEvent(this, 727, "update" + title));
                break;
        }
    }

    @Override
    public void reset() {
        if (textField instanceof JFormattedTextField formattedTextField)
            lastSetting = formattedTextField.getValue();
        else
            lastSetting = textField.getText();
    }
}
