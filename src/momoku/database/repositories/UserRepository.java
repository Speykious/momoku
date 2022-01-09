package momoku.database.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import momoku.database.models.User;

public final class UserRepository extends Repository<User, String> {
    public static final UserRepository REPOSITORY = new UserRepository();

    private UserRepository() {
        super("Users", "username", Arrays.asList(
                "username",
                "password",
                "current_room",
                "playing",
                "ready",
                "games_won",
                "current_score",
                "creation_date"));
    }

    @Override
    public User get(ResultSet result) throws SQLException {
        return new User(
                result.getString("username"),
                result.getString("password"),
                RoomRepository.REPOSITORY.get(result.getInt("current_room")),
                result.getBoolean("playing"),
                result.getBoolean("ready"),
                result.getInt("games_won"),
                result.getInt("current_score"),
                result.getDate("creation_date"));
    }

    @Override
    protected void populateColumns(PreparedStatement statement, int i, User user) throws SQLException {
        statement.setString(i++, user.getUsername());
        statement.setString(i++, user.getPassword());
        statement.setInt(i++, user.getCurrentRoom().getId());
        statement.setBoolean(i++, user.isPlaying());
        statement.setBoolean(i++, user.isReady());
        statement.setInt(i++, user.getGamesWon());
        statement.setInt(i++, user.getCurrentScore());
        statement.setDate(i++, user.getCreationDate());
    }

    @Override
    protected void populatePrimaryKey(PreparedStatement saveStatement, int i, String username) throws SQLException {
        saveStatement.setString(i, username);
    }
}
