import java.awt.Font;
import java.awt.Toolkit;
import java.util.Date;
import java.util.Random;
import java.util.random.RandomGenerator;

public final class GlobalSettings {
    public static final Font DEFAULT_FONT = new Font("Roboto Regular", Font.PLAIN, 20);
    public static final Toolkit TOOLKIT = Toolkit.getDefaultToolkit();
    public static final RandomGenerator RANDOM = new Random((new Date()).getTime());
}
