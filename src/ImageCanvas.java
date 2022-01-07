import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

public class ImageCanvas extends Canvas {
    private String imagePath;

    public ImageCanvas(String path) {
        imagePath = path;
        setSize(500, 500);
    }

    @Override
    public void paint(Graphics g) {
        Image image = getToolkit().getImage(imagePath);
        g.drawImage(image, 0, 0, this);
    }
}
