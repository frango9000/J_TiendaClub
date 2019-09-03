package app.data.casteldao.dao;

import app.data.casteldao.index.SimpleUniqueIndexIdImpl;
import app.data.casteldao.model.IEntity;

public class DataSourceIdImpl<V extends IEntity<Integer>> extends DataSourceId<V, Integer> {

    public DataSourceIdImpl(String tableName, Class<V> clazz) {
        super(tableName, clazz);
        setIdIndex(new SimpleUniqueIndexIdImpl<>(dao));
    }
}
