package momoku.database.models;

import java.sql.Date;

import momoku.database.repositories.RoomRepository;

public final class Room extends Model<Room, Integer, RoomRepository> {
    private String title;
    private String pass;
    private int owner;
    private boolean playing;
    private int rounds;
    private Date creationDate;

    public Room(
        int id,
        String title,
        String pass,
        int owner,
        boolean playing,
        int rounds,
        Date creationDate
    ) {
        super(id, RoomRepository.REPOSITORY);
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

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
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
