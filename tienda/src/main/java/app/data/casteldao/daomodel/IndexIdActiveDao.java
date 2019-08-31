package app.data.casteldao.daomodel;

import app.data.casteldao.index.MultiIndexActive;

public class IndexIdActiveDao<V extends Activable> extends IndexIdDao<V> {


    private MultiIndexActive<V> indexActive;

    public IndexIdActiveDao(String tableName, Class<V> clazz) {
        super(tableName, clazz);
        indexActive = new MultiIndexActive<>(dataSource);
        indexes.add(indexActive);
    }

    public MultiIndexActive<V> getIndexActive() {
        return indexActive;
    }
}
