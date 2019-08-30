package tiendaclub.data.dao.core;

import tiendaclub.data.framework.DataSource;
import tiendaclub.data.framework.index.UniqueIndexId;
import tiendaclub.data.framework.model.IPersistible;

public class IndexIdDao<V extends IPersistible> extends PersistibleDao<V> {

    private UniqueIndexId<V> indexId = new UniqueIndexId<>(getDataSource());

    public IndexIdDao(String tableName, Class<V> clazz) {
        this.dataSource = new DataSource<V>(tableName, getIndexes(), clazz);
        indexes.add(indexId);
    }

    public UniqueIndexId<V> getIndexId() {
        return indexId;
    }
}
