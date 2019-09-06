package casteldao.datasource;

import casteldao.DataSession;
import casteldao.index.SimpleUniqueIndexIdImpl;
import casteldao.model.IEntity;

public class DataSourceIdImpl<V extends IEntity<Integer>> extends DataSourceId<V, Integer> {

    public DataSourceIdImpl(DataSession session, String tableName, Class<V> clazz) {
        super(session, tableName, clazz);
        setIdIndex(new SimpleUniqueIndexIdImpl<>(dao));
    }
}
