package momoku.database.models;

import momoku.database.repositories.IRepository;

public abstract class Model<S extends Model<S, K, R>, K, R extends IRepository<R, S, K>> {
    protected final K primaryKey;
    protected final R repository;

    protected Model(K primaryKey, R repository) {
        this.primaryKey = primaryKey;
        this.repository = repository;
    }

    public R getRepository() {
        return repository;
    }

    public K getPrimaryKey() {
        return primaryKey;
    }
}
