package momoku.database.models;

import momoku.database.repositories.IRepository;

public abstract class Model<S extends Model<S, T, U>, T, U extends IRepository<U, S, T>> {
    protected final T primaryKey;
    protected final U repository;

    protected Model(T primaryKey, U repository) {
        this.primaryKey = primaryKey;
        this.repository = repository;
    }

    public U getRepository() {
        return repository;
    }

    public T getPrimaryKey() {
        return primaryKey;
    }
}
