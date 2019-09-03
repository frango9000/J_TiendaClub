package casteldao.dao;

import casteldao.index.SimpleUniqueIndexIdImpl;
import casteldao.model.IEntity;

public class DataSourceIdImpl<V extends IEntity<Integer>> extends DataSourceId<V, Integer> {

    public DataSourceIdImpl(String tableName, Class<V> clazz) {
        super(tableName, clazz);
        setIdIndex(new SimpleUniqueIndexIdImpl<>(dao));
    }
}
