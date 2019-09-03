package app.data.casteldao.dao;

import app.data.casteldao.model.IEntity;

public class IndexIdDataSourceImpl<V extends IEntity<Integer>> extends IndexIdDataSource<V, Integer> {

    public IndexIdDataSourceImpl(String tableName, Class<V> clazz) {
        super(tableName, clazz);
    }
}
