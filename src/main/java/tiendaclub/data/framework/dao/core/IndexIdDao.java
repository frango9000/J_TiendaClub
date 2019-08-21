package tiendaclub.data.framework.dao.core;

import tiendaclub.data.framework.index.UniqueIndexId;
import tiendaclub.model.models.core.IPersistible;

public class IndexIdDao<V extends IPersistible> extends PersistibleDao<V> {

    private UniqueIndexId<V> indexId = new UniqueIndexId<>(getDataSource());

    public IndexIdDao(String tableName) {
        super(tableName);
        indexes.add(indexId);
    }

    public UniqueIndexId<V> getIndexId() {
        return indexId;
    }
}
