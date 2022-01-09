package momoku.database.repositories;

import java.sql.SQLException;
import java.util.List;

import momoku.database.models.Model;

public interface IRepository<S extends IRepository<S, T, U>, T extends Model<T, U, S>, U> {
    public T get(U primaryKey) throws SQLException;
    
    public T update(T model) throws SQLException;
    
    public T save(T model) throws SQLException;
    
    public boolean delete(U primaryKey) throws SQLException;

    public List<T> list() throws SQLException;
}
