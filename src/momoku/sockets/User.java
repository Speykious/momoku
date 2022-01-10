package momoku.sockets;

import momoku.GlobalSettings;
public class User {

    private long points;
    private int imageIndex;
    String name;
    String channel;
    int score;

    public User() {
        reset();
    }

    public void reset() {
        points = 0;
        updateImageIndex();
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    };

    public void incrementScore() {
        this.score++;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChannel() {
        return this.channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
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

