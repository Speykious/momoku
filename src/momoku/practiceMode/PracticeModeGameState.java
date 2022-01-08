package momoku.practiceMode;

import momoku.GlobalSettings;

public class PracticeModeGameState {
    private long points;
    private int imageIndex;

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
        updateImageIndex();
    }

    public int updateImageIndex() {
        int previousIndex;
        do {
            previousIndex = imageIndex;
            imageIndex = Math.abs(GlobalSettings.RANDOM.nextInt()) % GlobalSettings.IMAGE_FILES.length;
        } while (imageIndex == previousIndex);
        return imageIndex;
    }
}
