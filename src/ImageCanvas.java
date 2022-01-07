import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

public class ImageCanvas extends Canvas {
    private String imagePath;

    public ImageCanvas() {
        setSize(600, 600);
    }

    public ImageCanvas(String path) {
        this();
        setImagePath(path);
    }

    @Override
    public void paint(Graphics g) {
        Image image = getToolkit().getImage(imagePath);
        g.drawImage(image, 0, 0, this);
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String path) {
        imagePath = path;
    }
}
