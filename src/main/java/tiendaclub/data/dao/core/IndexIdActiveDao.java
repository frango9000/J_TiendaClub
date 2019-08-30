package tiendaclub.data.dao.core;

import tiendaclub.data.framework.index.MultiIndexActive;
import tiendaclub.model.models.core.Activable;

public class IndexIdActiveDao<V extends Activable> extends IndexIdDao<V> {


    private MultiIndexActive<V> indexActive = new MultiIndexActive<>(dataSource);

    public IndexIdActiveDao(String tableName, Class<V> clazz) {
        super(tableName, clazz);
        indexes.add(indexActive);
    }

    public MultiIndexActive<V> getIndexActive() {
        return indexActive;
    }
}
