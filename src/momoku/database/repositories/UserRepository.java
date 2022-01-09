package momoku.database.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import momoku.database.Database;
import momoku.database.models.User;

public final class UserRepository implements IRepository<UserRepository, User, String> {
    public static final UserRepository REPOSITORY = new UserRepository();

    private PreparedStatement getStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement saveStatement;
    private PreparedStatement deleteStatement;
    private PreparedStatement listStatement;

    private UserRepository() {
        Connection c = Database.connection;

        try {
            getStatement = c.prepareStatement("SELECT * FROM Users WHERE username = ? LIMIT 1");
            updateStatement = c.prepareStatement(
                    "UPDATE Users SET username = ?, password = ?, current_room = ?, playing = ?, ready = ?, games_won = ?, current_score = ?, creation_date = ? WHERE username = ?");
            saveStatement = c.prepareStatement(
                    "INSERT INTO Users (username, password, current_room, playing, ready, games_won, current_score, creation_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            deleteStatement = c.prepareStatement("DELETE FROM Users WHERE username = ?");
            listStatement = c.prepareStatement("SELECT * FROM Users");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public User get(String username) throws SQLException {
        getStatement.setString(1, username);
        ResultSet result = getStatement.executeQuery();
        return get(result);
    }

    public User get(ResultSet result) throws SQLException {
        if (!result.next())
            return null;

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
    public User update(User user) throws SQLException {
        int i = 1;
        updateStatement.setString(i++, user.getUsername());
        updateStatement.setString(i++, user.getPassword());
        updateStatement.setInt(i++, user.getCurrentRoom().getId());
        updateStatement.setBoolean(i++, user.isPlaying());
        updateStatement.setBoolean(i++, user.isReady());
        updateStatement.setInt(i++, user.getGamesWon());
        updateStatement.setInt(i++, user.getCurrentScore());
        updateStatement.setDate(i++, user.getCreationDate());
        updateStatement.setString(i++, user.getUsername()); // WHERE [...]
        updateStatement.executeUpdate();
        return user;
    }

    @Override
    public User save(User user) throws SQLException {
        int i = 1;
        saveStatement.setString(i++, user.getUsername());
        saveStatement.setString(i++, user.getPassword());
        saveStatement.setInt(i++, user.getCurrentRoom().getId());
        saveStatement.setBoolean(i++, user.isPlaying());
        saveStatement.setBoolean(i++, user.isReady());
        saveStatement.setInt(i++, user.getGamesWon());
        saveStatement.setInt(i++, user.getCurrentScore());
        saveStatement.setDate(i++, user.getCreationDate());
        saveStatement.executeUpdate();
        return user;
    }

    @Override
    public boolean delete(String username) throws SQLException {
        deleteStatement.setString(1, username);
        return deleteStatement.execute();
    }

    @Override
    public List<User> list() throws SQLException {
        List<User> users = new ArrayList<User>();
        ResultSet result = listStatement.executeQuery();
        for (User user = get(result); user != null; user = get(result))
            users.add(user);
        return users;
    }
}
