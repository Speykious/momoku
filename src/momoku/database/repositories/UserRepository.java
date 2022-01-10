package momoku.database.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;

import momoku.database.Database;
import momoku.database.models.Room;
import momoku.database.models.User;

public final class UserRepository extends Repository<User, String> {
    public static final UserRepository REPOSITORY = new UserRepository();
    private volatile PreparedStatement roomStatement;

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

        Connection c = Database.connection;
        try {
            roomStatement = c.prepareStatement("SELECT * FROM Users WHERE current_room = ?");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public List<User> getUsersInRoom(Room room) throws SQLException {
        roomStatement.setInt(1, room.getId());
        return getModels(roomStatement);
    }

    @Override
    public User get(ResultSet result) throws SQLException {
        User user = new User(
                result.getString("username"),
                result.getString("password"),
                null,
                result.getBoolean("playing"),
                result.getBoolean("ready"),
                result.getInt("games_won"),
                result.getInt("current_score"),
                result.getDate("creation_date"));
        cache(user);
        user.setCurrentRoom(RoomRepository.REPOSITORY.get(result.getInt("current_room")));
        return user;
    }

    @Override
    protected void populateColumns(PreparedStatement statement, int i, User user) throws SQLException {
        Room room = user.getCurrentRoom();
        statement.setString(i++, user.getUsername());
        statement.setString(i++, user.getPassword());
        if (room == null)
            statement.setNull(i++, Types.INTEGER);
        else
            statement.setInt(i++, room.getId());
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
