package momoku.practiceMode;

public class PracticeGameState {
    private long points;

    public PracticeGameState() {
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
