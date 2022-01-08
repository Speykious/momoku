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
import javax.swing.border.EmptyBorder;

public final class MainWindow extends CenteredWindow implements ActionListener {
    public static final MainWindow WINDOW = new MainWindow();

    private JPanel cardPanel;
    private CardLayout cardLayout;

    private HashMap<String, JPanel> cards;

    private MainWindow() {
        super(false);
        cards = new HashMap<String, JPanel>();
    }

    @Override
    public void init() {
        setTitle("Mōmoku");

        cards.put("mainMenu", new MainMenuPanel(this));
        cards.put("practiceMode", new PracticeModePanel(this));

        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        cardPanel.setBorder(new EmptyBorder(100, 50, 100, 50));

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
