package momoku.database.models;

import java.sql.Date;

import momoku.database.repositories.UserRepository;

public class User extends Model<User, String, UserRepository> {
    private String password;
    private int currentRoom;
    private boolean playing;
    private boolean ready;
    private int gamesWon;
    private int currentScore;
    private Date creationDate;

    public User(
        String username,
        String password,
        int currentRoom,
        boolean playing,
        boolean ready,
        int gamesWon,
        int currentScore,
        Date creationDate
    ) {
        super(username, UserRepository.REPOSITORY);
        this.password = password;
        this.currentRoom = currentRoom;
        this.playing = playing;
        this.ready = ready;
        this.gamesWon = gamesWon;
        this.currentScore = currentScore;
        this.creationDate = creationDate;
    }

    public String getUsername() {
        return primaryKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(int currentRoom) {
        this.currentRoom = currentRoom;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
