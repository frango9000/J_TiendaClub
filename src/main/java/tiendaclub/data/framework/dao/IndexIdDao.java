package tiendaclub.data.framework.dao;

import tiendaclub.data.framework.index.IndexId;
import tiendaclub.model.models.abstracts.Persistible;

public class IndexIdDao<V extends Persistible> extends PersistibleDao<V> {

    private IndexId<V> idIndex = new IndexId<>(dataSource);

    public IndexIdDao(String TABLE_NAME) {
        super(TABLE_NAME);
        indexes.add(idIndex);
    }

    public IndexId<V> getIdIndex() {
        return idIndex;
    }
}
