package casteldao.datasource;

import casteldao.DataSession;
import casteldao.index.SetMultiMapIndexActiveImpl;
import casteldao.model.ActivableEntity;

public class DataSourceIdActive<V extends ActivableEntity> extends DataSourceIdImpl<V> {


    private SetMultiMapIndexActiveImpl<V> indexActive;

    public DataSourceIdActive(DataSession session, String tableName, Class<V> clazz) {
        super(session, tableName, clazz);
        indexActive = new SetMultiMapIndexActiveImpl<>(dao);
        indexes.add(indexActive);
    }

    public SetMultiMapIndexActiveImpl<V> getIndexActive() {
        return indexActive;
    }
}
