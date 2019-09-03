package casteldao.dao;

import casteldao.index.SetMultiMapIndexActiveImpl;
import casteldao.model.ActivableEntity;

public class DataSourceIdActive<V extends ActivableEntity> extends DataSourceIdImpl<V> {


    private SetMultiMapIndexActiveImpl<V> indexActive;

    public DataSourceIdActive(String tableName, Class<V> clazz) {
        super(tableName, clazz);
        indexActive = new SetMultiMapIndexActiveImpl<>(dao);
        indexes.add(indexActive);
    }

    public SetMultiMapIndexActiveImpl<V> getIndexActive() {
        return indexActive;
    }
}
