package momoku;

import momoku.components.CenteredWindow;

import java.awt.event.ActionEvent;
import java.awt.CardLayout;

import javax.swing.JPanel;

public class MainWindow extends CenteredWindow {
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public MainWindow(boolean visible) {
        super(visible);
    }

    @Override
    public void init() {
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        momoku.mainMenu.MainWindow mainMenu = new momoku.mainMenu.MainWindow();
        mainMenu.setParent(this);
        mainMenu.run();
        cardPanel.add(mainMenu.getMainPanel(), "mainMenu");

        momoku.practiceMode.MainWindow practiceMode = new momoku.practiceMode.MainWindow();
        practiceMode.setParent(this);
        practiceMode.run();
        cardPanel.add(practiceMode.getMainPanel(), "practiceMode");

        add(cardPanel);
        pack();
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
