package momoku.practiceMode;

import java.sql.SQLException;
import java.util.List;

import momoku.database.models.Image;
import momoku.database.repositories.ImageRepository;

public class PracticeModeGameState {
    private long points;
    private Image image;

    public PracticeModeGameState() {
        reset();
    }

    public long getPoints() {
        return points;
    }

    public void addPoint() {
        points++;
    }

    public void reset() {
        points = 0;
        updateImage();
    }

    public Image updateImage() {
        try {
            List<Image> images = ImageRepository.REPOSITORY.getRandoms(2);
            if (image != images.get(0))
                image = images.get(0);
            else
                image = images.get(1);
            return image;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
