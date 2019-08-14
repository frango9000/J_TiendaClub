package tiendaclub.data.framework.dao;

import tiendaclub.data.framework.index.SimpleIndexId;
import tiendaclub.model.models.abstracts.Persistible;

public class IdIndexDao<V extends Persistible> extends PersistibleDao<V> {

    private SimpleIndexId<V> idIndex = new SimpleIndexId<>(dataSource);

    public IdIndexDao(String TABLE_NAME) {
        super(TABLE_NAME);
        indexes.add(idIndex);
    }

    public SimpleIndexId<V> getIdIndex() {
        return idIndex;
    }
}
