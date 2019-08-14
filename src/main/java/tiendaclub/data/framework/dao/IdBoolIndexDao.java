package tiendaclub.data.framework.dao;

import tiendaclub.data.framework.index.MultiMapIndexBoolean;
import tiendaclub.model.models.abstracts.Activable;

public class IdBoolIndexDao<T extends Activable> extends IdIndexDao<T> {


    private MultiMapIndexBoolean<T> activeIndex = new MultiMapIndexBoolean<>(dataSource);

    public IdBoolIndexDao(String TABLE_NAME) {
        super(TABLE_NAME);
        indexes.add(activeIndex);
    }

    public MultiMapIndexBoolean<T> getActiveIndex() {
        return activeIndex;
    }
}
