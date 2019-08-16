package tiendaclub.data.framework.dao;

import tiendaclub.data.framework.index.IndexActive;
import tiendaclub.model.models.abstracts.Activable;

public class IndexIdActiveDao<T extends Activable> extends IndexIdDao<T> {


    private IndexActive<T> activeIndex = new IndexActive<>(dataSource);

    public IndexIdActiveDao(String TABLE_NAME) {
        super(TABLE_NAME);
        indexes.add(activeIndex);
    }

    public IndexActive<T> getActiveIndex() {
        return activeIndex;
    }
}
