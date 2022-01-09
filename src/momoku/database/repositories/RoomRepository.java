package momoku.database.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import momoku.database.Database;
import momoku.database.models.Room;

public class RoomRepository implements IRepository<RoomRepository, Room, Integer> {
    public static final RoomRepository REPOSITORY = new RoomRepository();

    private PreparedStatement getStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement saveStatement;
    private PreparedStatement deleteStatement;
    private PreparedStatement listStatement;

    private RoomRepository() {
        Connection c = Database.connection;

        try {
            getStatement = c.prepareStatement("SELECT * FROM Rooms WHERE id = ? LIMIT 1");
            updateStatement = c.prepareStatement(
                    "UPDATE Rooms SET id = ?, title = ?, pass = ?, owner = ?, playing = ?, rounds = ?, creationDate = ? WHERE id = ?");
            saveStatement = c.prepareStatement(
                    "INSERT INTO Rooms (id, title, pass, owner, playing, rounds, creationDate) VALUES (?, ?, ?, ?, ?, ?, ?)");
            deleteStatement = c.prepareStatement("DELETE FROM Rooms WHERE id = ?");
            listStatement = c.prepareStatement("SELECT * FROM Rooms");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public Room get(Integer id) throws SQLException {
        getStatement.setInt(1, id);
        ResultSet result = getStatement.executeQuery();
        return get(result);
    }

    public Room get(ResultSet result) throws SQLException {
        if (!result.next())
            return null;

        String ownerUsername = result.getString("owner");

        return new Room(
                result.getInt("id"),
                result.getString("title"),
                result.getString("pass"),
                UserRepository.REPOSITORY.get(ownerUsername),
                result.getBoolean("playing"),
                result.getInt("rounds"),
                result.getDate("creation_date"));
    }

    @Override
    public Room update(Room room) throws SQLException {
        int i = 1;
        updateStatement.setInt(i++, room.getId());
        updateStatement.setString(i++, room.getTitle());
        updateStatement.setString(i++, room.getPass());
        updateStatement.setString(i++, room.getOwner().getUsername());
        updateStatement.setBoolean(i++, room.isPlaying());
        updateStatement.setInt(i++, room.getRounds());
        updateStatement.setDate(i++, room.getCreationDate());
        updateStatement.setInt(i++, room.getId()); // WHERE [...]
        updateStatement.executeUpdate();
        return room;
    }

    @Override
    public Room save(Room room) throws SQLException {
        int i = 1;
        saveStatement.setInt(i++, room.getId());
        saveStatement.setString(i++, room.getTitle());
        saveStatement.setString(i++, room.getPass());
        saveStatement.setString(i++, room.getOwner().getUsername());
        saveStatement.setBoolean(i++, room.isPlaying());
        saveStatement.setInt(i++, room.getRounds());
        saveStatement.setDate(i++, room.getCreationDate());
        saveStatement.executeUpdate();
        return room;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        deleteStatement.setInt(1, id);
        return deleteStatement.execute();
    }

    @Override
    public List<Room> list() throws SQLException {
        List<Room> rooms = new ArrayList<Room>();
        ResultSet result = listStatement.executeQuery();
        for (Room room = get(result); room != null; room = get(result))
            rooms.add(room);
        return rooms;
    }
}
