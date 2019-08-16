package tiendaclub.data.framework.dao;

import tiendaclub.data.framework.index.IndexActive;
import tiendaclub.model.models.abstracts.Activable;

public class IndexIdActiveDao<V extends Activable> extends IndexIdDao<V> {


    private IndexActive<V> indexActive = new IndexActive<>(dataSource);

    public IndexIdActiveDao(String TABLE_NAME) {
        super(TABLE_NAME);
        indexes.add(indexActive);
    }

    public IndexActive<V> getIndexActive() {
        return indexActive;
    }
}
