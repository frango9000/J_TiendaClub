package app.data.casteldao.dao;

import app.data.casteldao.GenericDao;
import app.data.casteldao.index.core.UniqueIndexId;
import app.data.casteldao.model.IEntity;
import java.io.Serializable;
import java.util.Set;

public class IndexIdDataSource<V extends IEntity<I>, I extends Serializable> extends DataSource<V, I> {

    private UniqueIndexId<V, I> indexId;

    public IndexIdDataSource(String tableName, Class<V> clazz) {
        this.dao     = new GenericDao<>(tableName, getIndexes(), clazz);
        this.indexId = new UniqueIndexId<>(dao);
        indexes.add(indexId);
    }

    public UniqueIndexId<V, I> getById() {
        return indexId;
    }

    public Set<V> getAll() {
        return indexId.getValues();
    }

    public Set<V> getAllCache() {
        return indexId.getCacheValues();
    }
}
