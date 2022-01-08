package momoku;

public class PracticeGameState {
    private long points;

    public PracticeGameState() {
        points = 0;
    }

    public long getPoints() {
        return points;
    }

    public void addPoint() {
        points++;
    }
}
