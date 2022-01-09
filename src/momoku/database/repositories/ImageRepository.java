package momoku.database.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import momoku.database.models.Image;

public final class ImageRepository extends Repository<ImageRepository, Image, String> {
    public static final ImageRepository REPOSITORY = new ImageRepository();

    private ImageRepository() {
        super("Images", "filename", Arrays.asList(
                "filename",
                "whoisthis"));
    }

    @Override
    protected void populatePrimaryKey(PreparedStatement statement, int i, String primaryKey) throws SQLException {
        statement.setString(i, primaryKey);
    }

    @Override
    protected void populateColumns(PreparedStatement statement, int i, Image model) throws SQLException {
        statement.setString(i++, model.getFilename());
        statement.setString(i++, model.getWhoisthis());
    }

    @Override
    protected Image get(ResultSet result) throws SQLException {
        return new Image(
                result.getString("filename"),
                result.getString("whoisthis"));
    }
}
