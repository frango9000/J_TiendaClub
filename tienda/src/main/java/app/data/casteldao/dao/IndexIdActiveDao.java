package app.data.casteldao.dao;

import app.data.casteldao.index.MultiIndexActive;
import app.data.casteldao.model.ActivableEntity;

public class IndexIdActiveDao<V extends ActivableEntity> extends IndexIdDataSourceImpl<V> {


    private MultiIndexActive<V> indexActive;

    public IndexIdActiveDao(String tableName, Class<V> clazz) {
        super(tableName, clazz);
        indexActive = new MultiIndexActive<>(dao);
        indexes.add(indexActive);
    }

    public MultiIndexActive<V> getIndexActive() {
        return indexActive;
    }
}
