package momoku;

import java.io.IOException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;

import momoku.sockets.MomokuClient;

public class Main {
    public static void main(String[] args) {
        // Setup modern look and feel
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            UIManager.put("defaultFont", GlobalSettings.DEFAULT_FONT);
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        try {
            MomokuClient.INSTANCE.connectToServer();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        SwingUtilities.invokeLater(MainWindow.WINDOW);
    }
}