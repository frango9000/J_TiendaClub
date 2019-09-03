package app.data.casteldao.dao;

import app.data.casteldao.GenericDao;
import app.data.casteldao.index.core.SimpleMapIndex;
import app.data.casteldao.model.IEntity;
import java.io.Serializable;
import java.util.Set;

public class DataSourceId<V extends IEntity<I>, I extends Serializable> extends DataSource<V, I> {

    private SimpleMapIndex<I, V, I> idIndex;

    public DataSourceId(String tableName, Class<V> clazz) {
        this.dao     = new GenericDao<>(tableName, getIndexes(), clazz);
    }

    protected void setIdIndex(SimpleMapIndex<I, V, I> idIndex) {
        this.idIndex = idIndex;
        indexes.add(idIndex);
    }

    public SimpleMapIndex<I, V, I> getById() {
        return idIndex;
    }

    public Set<V> getAll() {
        return idIndex.getValues();
    }

    public Set<V> getAllCache() {
        return idIndex.getCacheValues();
    }
}
