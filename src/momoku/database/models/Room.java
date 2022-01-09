package momoku.database.models;

import java.sql.Date;

public final class Room extends Model<Integer> {
    private String title;
    private String pass;
    private User owner;
    private boolean playing;
    private int rounds;
    private Date creationDate;

    public Room(
            int id,
            String title,
            String pass,
            User owner,
            boolean playing,
            int rounds,
            Date creationDate) {
        super(id);
        this.title = title;
        this.pass = pass;
        this.owner = owner;
        this.playing = playing;
        this.rounds = rounds;
        this.creationDate = creationDate;
    }

    public int getId() {
        return primaryKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
