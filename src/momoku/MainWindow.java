package momoku;

import momoku.components.CenteredWindow;
import momoku.mainMenu.MainMenuPanel;
import momoku.practiceMode.PracticeModePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map.Entry;
import java.awt.CardLayout;

import javax.swing.JPanel;

public class MainWindow extends CenteredWindow implements ActionListener {
    private JPanel cardPanel;
    private CardLayout cardLayout;

    private HashMap<String, JPanel> cards;

    public MainWindow(boolean resizable) {
        super(resizable);
        cards = new HashMap<String, JPanel>();
    }

    public MainWindow() {
        this(false);
    }

    @Override
    public void init() {
        setTitle("Mōmoku");

        cards.put("mainMenu", new MainMenuPanel(this));
        cards.put("practiceMode", new PracticeModePanel(this));

        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        for (Entry<String, JPanel> entry : cards.entrySet())
            cardPanel.add(entry.getValue(), entry.getKey());

        add(cardPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "exit":
                System.exit(0);
                break;
            default:
                cardLayout.show(cardPanel, e.getActionCommand());
                break;
        }
    }
}
