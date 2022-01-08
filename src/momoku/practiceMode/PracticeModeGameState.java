package momoku.practiceMode;

public class PracticeModeGameState {
    private long points;

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
    }
}
