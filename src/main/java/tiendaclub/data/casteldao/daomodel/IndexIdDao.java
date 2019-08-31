package tiendaclub.data.casteldao.daomodel;

import java.util.Set;
import tiendaclub.data.casteldao.DataSource;
import tiendaclub.data.casteldao.index.UniqueIndexId;

public class IndexIdDao<V extends IPersistible> extends PersistibleDao<V> {

    private UniqueIndexId<V> indexId;

    public IndexIdDao(String tableName, Class<V> clazz) {
        this.dataSource = new DataSource<>(tableName, getIndexes(), clazz);
        this.indexId    = new UniqueIndexId<>(dataSource);
        indexes.add(indexId);
    }

    public UniqueIndexId<V> getById() {
        return indexId;
    }

    public Set<V> getAll() {
        return indexId.getValues();
    }

    public Set<V> getAllCache() {
        return indexId.getCacheValues();
    }
}
