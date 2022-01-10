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

    public boolean equals(Model<K> other) {
        return primaryKey.equals(other.primaryKey);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (this == obj)
            return true;
        
        if (getClass() != obj.getClass())
            return false;

        final Model<K> other = (Model<K>)obj;
        return equals(other);
    }
}
