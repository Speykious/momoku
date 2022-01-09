package momoku;

import momoku.components.CenteredWindow;
import momoku.components.Screen;
import momoku.multiplayer.RoomSelectionScreen;
import momoku.practiceMode.PracticeModeScreen;

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

    private HashMap<String, Screen> cards;

    private MainWindow() {
        super(false);
        cards = new HashMap<String, Screen>();
    }

    @Override
    public void init() {
        setTitle("Mōmoku");

        cards.put("mainMenu", new MainMenuScreen());
        cards.put("practiceMode", new PracticeModeScreen());
        cards.put("multiplayer", new RoomSelectionScreen());

        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        cardPanel.setBorder(new EmptyBorder(50, 100, 50, 100));

        for (Entry<String, Screen> entry : cards.entrySet())
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
                cards.get(e.getActionCommand()).reset();
                break;
        }
    }
}
