package tiendaclub.data.casteldao.daomodel;

import tiendaclub.data.casteldao.DataSource;
import tiendaclub.data.casteldao.index.UniqueIndexId;

public class IndexIdDao<V extends IPersistible> extends PersistibleDao<V> {

    private UniqueIndexId<V> indexId = new UniqueIndexId<>(getDataSource());

    public IndexIdDao(String tableName, Class<V> clazz) {
        this.dataSource = new DataSource<V>(tableName, getIndexes(), clazz);
        indexes.add(indexId);
    }

    public UniqueIndexId<V> getIndexId() {
        return indexId;
    }
}
