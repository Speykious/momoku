package momoku.database.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import momoku.database.models.Model;

public interface IRepository<S extends IRepository<S, M, K>, M extends Model<M, K, S>, K> {
    public M get(K primaryKey) throws SQLException;

    public M get(ResultSet result) throws SQLException;
    
    public M update(M model) throws SQLException;
    
    public M save(M model) throws SQLException;
    
    public boolean delete(K primaryKey) throws SQLException;

    public List<M> list() throws SQLException;
}
