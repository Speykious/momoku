package momoku.database.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import momoku.database.models.Image;

public class ImageRepository extends Repository<ImageRepository, Image, Integer> {
    public static final ImageRepository REPOSITORY = new ImageRepository();

    private ImageRepository() {
        super("Images", "id", Arrays.asList(
                "id",
                "filename",
                "whoisthis"));
    }

    @Override
    protected void populatePrimaryKey(PreparedStatement statement, int i, Integer primaryKey) throws SQLException {
        statement.setInt(i, primaryKey);
    }

    @Override
    protected void populateColumns(PreparedStatement statement, int i, Image model) throws SQLException {
        statement.setInt(i++, model.getId());
        statement.setString(i++, model.getFilename());
        statement.setString(i++, model.getWhoisthis());
    }

    @Override
    protected Image get(ResultSet result) throws SQLException {
        return new Image(
                result.getInt("id"),
                result.getString("filename"),
                result.getString("whoisthis"));
    }
}
