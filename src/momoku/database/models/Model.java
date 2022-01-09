package momoku.database.models;

/**
 * Abstract class to define objects that represent rows of specific database
 * tables.
 */
public abstract class Model<K> {
    /** The row's primary key value. */
    protected final K primaryKey;

    protected Model(K primaryKey) {
        this.primaryKey = primaryKey;
    }

    public K getPrimaryKey() {
        return primaryKey;
    }
}
