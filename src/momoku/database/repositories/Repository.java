package momoku.database.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import momoku.database.Database;
import momoku.database.models.Model;

public abstract class Repository<S extends Repository<S, M, K>, M extends Model<M, K, S>, K> {
    private final String tableName;
    private final String primaryKeyName;
    private final List<String> columns;
    private final HashMap<K, M> cache;

    private PreparedStatement getStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement saveStatement;
    private PreparedStatement deleteStatement;
    private PreparedStatement listStatement;

    protected Repository(String tableName, String primaryKeyName, List<String> columns) {
        this.tableName = tableName;
        this.primaryKeyName = primaryKeyName;
        this.columns = columns;
        cache = new HashMap<K, M>();

        Connection c = Database.connection;

        try {
            var comma = Collectors.joining(", ");
            String columnsSet = columns.stream().map((String column) -> {
                return column + " = ?";
            }).collect(comma);
            String columnsInto = columns.stream().collect(comma);
            String columnsValues = columns.stream().map((String _column) -> {
                return "?";
            }).collect(comma);

            getStatement = c
                    .prepareStatement("SELECT * FROM " + tableName + " WHERE " + primaryKeyName + " = ? LIMIT 1");
            updateStatement = c.prepareStatement("UPDATE " + tableName + " SET " + columnsSet + " WHERE id = ?");
            saveStatement = c.prepareStatement(
                    "INSERT INTO " + tableName + " (" + columnsInto + ") VALUES (" + columnsValues + ")");
            deleteStatement = c.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?");
            listStatement = c.prepareStatement("SELECT * FROM " + tableName);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    protected abstract void populatePrimaryKey(PreparedStatement statement, int i, K primaryKey) throws SQLException;

    protected abstract void populateColumns(PreparedStatement statement, int i, M model) throws SQLException;

    protected abstract M get(ResultSet result) throws SQLException;

    public void cache(M model) {
        cache.put(model.getPrimaryKey(), model);
    }

    public void cacheAll() throws SQLException {
        for (M model : list())
            cache(model);
    }

    public M get(K key) throws SQLException {
        M model = cache.get(key);
        if (model != null)
            return model;

        populatePrimaryKey(getStatement, 1, key);
        ResultSet result = getStatement.executeQuery();
        if (!result.next())
            return null;

        model = get(result);
        if (model != null)
            cache(model);

        return model;
    }

    public M update(M model) throws SQLException {
        populateColumns(updateStatement, 1, model);
        populatePrimaryKey(updateStatement, columns.size() + 1, model.getPrimaryKey());
        updateStatement.executeUpdate();
        return model;
    }

    public M save(M model) throws SQLException {
        cache(model);
        populateColumns(saveStatement, 1, model);
        saveStatement.executeUpdate();
        return model;
    }

    public boolean delete(K key) throws SQLException {
        cache.remove(key);
        populatePrimaryKey(deleteStatement, 1, key);
        return deleteStatement.execute();
    }

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