package tiendaclub.data.casteldao.daomodel;

import tiendaclub.data.casteldao.index.MultiIndexActive;

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
