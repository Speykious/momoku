package momoku;

import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.random.RandomGenerator;

public final class GlobalSettings {
    public static final Font DEFAULT_FONT = new Font("Roboto Regular", Font.PLAIN, 20);
    public static final Toolkit TOOLKIT = Toolkit.getDefaultToolkit();
    public static final RandomGenerator RANDOM = new Random((new Date()).getTime());
    public static final Timer TIMER = new Timer();

    public static final Font HEADER_FONT = DEFAULT_FONT.deriveFont(Font.BOLD, 50f);
    public static final Font FOOTER_FONT = DEFAULT_FONT.deriveFont(30f);

    public static final File IMAGE_DIRECTORY = new File("images");
    public static final File[] IMAGE_FILES = IMAGE_DIRECTORY.listFiles(new FilenameFilter() {
        public boolean accept(File dir, String filename) {
            String name = filename.toLowerCase();
            return name.endsWith(".png") || name.endsWith(".jpg");
        }
    });
}
