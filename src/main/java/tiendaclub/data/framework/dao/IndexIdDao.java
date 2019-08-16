package tiendaclub.data.framework.dao;

import tiendaclub.data.framework.index.IndexId;
import tiendaclub.model.models.abstracts.IPersistible;

public class IndexIdDao<V extends IPersistible> extends PersistibleDao<V> {

    private IndexId<V> indexId = new IndexId<>(dataSource);

    public IndexIdDao(String TABLE_NAME) {
        super(TABLE_NAME);
        indexes.add(indexId);
    }

    public IndexId<V> getIndexId() {
        return indexId;
    }
}
