package momoku.database.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import momoku.database.models.Room;

public final class RoomRepository extends Repository<Room, Integer> {
    public static final RoomRepository REPOSITORY = new RoomRepository();

    private RoomRepository() {
        super("Rooms", "id", Arrays.asList(
                "id",
                "title",
                "pass",
                "owner",
                "playing",
                "rounds",
                "creation_date"));
    }

    @Override
    public Room get(ResultSet result) throws SQLException {
        return new Room(
                result.getInt("id"),
                result.getString("title"),
                result.getString("pass"),
                UserRepository.REPOSITORY.get(result.getString("owner")),
                result.getBoolean("playing"),
                result.getInt("rounds"),
                result.getDate("creation_date"));
    }

    @Override
    protected void populateColumns(PreparedStatement statement, int i, Room room) throws SQLException {
        statement.setInt(i++, room.getId());
        statement.setString(i++, room.getTitle());
        statement.setString(i++, room.getPass());
        statement.setString(i++, room.getOwner().getUsername());
        statement.setBoolean(i++, room.isPlaying());
        statement.setInt(i++, room.getRounds());
        statement.setDate(i++, room.getCreationDate());
    }

    @Override
    protected void populatePrimaryKey(PreparedStatement statement, int i, Integer key) throws SQLException {
        statement.setInt(i, key);
    }
}
