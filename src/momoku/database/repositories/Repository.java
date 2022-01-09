package momoku.database.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

import momoku.database.Database;
import momoku.database.models.Model;

public abstract class Repository<S extends IRepository<S, M, K>, M extends Model<M, K, S>, K> implements IRepository<S, M, K> {
    private final String tableName;
    private final String primaryKeyName;
    private final List<String> columns;

    private PreparedStatement getStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement saveStatement;
    private PreparedStatement deleteStatement;
    private PreparedStatement listStatement;

    protected Repository(String tableName, String primaryKeyName, List<String> columns) {
        this.tableName = tableName;
        this.primaryKeyName = primaryKeyName;
        this.columns = columns;

        Connection c = Database.connection;

        try {
            var comma = Collectors.joining(", ");
            String columnsSet = columns.stream().map((String column) -> { return column + " = ?"; }).collect(comma);
            String columnsInto = columns.stream().collect(comma);
            String columnsValues = columns.stream().map((String _column) -> { return "?"; }).collect(comma);

            getStatement = c.prepareStatement("SELECT * FROM " + tableName + " WHERE " + primaryKeyName + " = ? LIMIT 1");
            updateStatement = c.prepareStatement("UPDATE " + tableName + " SET " + columnsSet + " WHERE id = ?");
            saveStatement = c.prepareStatement("INSERT INTO " + tableName + " (" + columnsInto + ") VALUES (" + columnsValues + ")");
            deleteStatement = c.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?");
            listStatement = c.prepareStatement("SELECT * FROM " + tableName);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


    protected abstract void populatePrimaryKey(PreparedStatement statement, int i, K primaryKey) throws SQLException;
    protected abstract void populateColumns(PreparedStatement statement, int i, M model) throws SQLException;

    @Override
    public M get(K key) throws SQLException {
        populatePrimaryKey(getStatement, 1, key);
        ResultSet result = getStatement.executeQuery();
        if (!result.next())
            return null;

        return get(result);
    }

    @Override
    public M update(M model) throws SQLException {
        populateColumns(updateStatement, 1, model);
        populatePrimaryKey(updateStatement, columns.size() + 1, model.getPrimaryKey());
        updateStatement.executeUpdate();
        return model;
    }

    @Override
    public M save(M model) throws SQLException {
        populateColumns(saveStatement, 1, model);
        saveStatement.executeUpdate();
        return model;
    }

    @Override
    public boolean delete(K key) throws SQLException {
        populatePrimaryKey(deleteStatement, 1, key);
        return deleteStatement.execute();
    }

    @Override
    public List<M> list() throws SQLException {
        List<M> models = new ArrayList<M>();
        ResultSet result = listStatement.executeQuery();
        for (M model = get(result); model != null; model = get(result))
            models.add(model);
        return models;
    }

    public String getTableName() {
        return tableName;
    }

    public String getPrimaryKeyName() {
        return primaryKeyName;
    }

    public List<String> getColumns() {
        return columns;
    }
}
