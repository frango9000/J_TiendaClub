package tiendaclub.data.framework.dao;

import tiendaclub.data.framework.index.SetMultiMapIndexBoolean;
import tiendaclub.model.models.abstracts.Activable;

public class IdBoolIndexDao<T extends Activable> extends IdIndexDao<T> {


    private SetMultiMapIndexBoolean<T> activeIndex = new SetMultiMapIndexBoolean<>(dataSource);

    public IdBoolIndexDao(String TABLE_NAME) {
        super(TABLE_NAME);
        indexes.add(activeIndex);
    }

    public SetMultiMapIndexBoolean<T> getActiveIndex() {
        return activeIndex;
    }
}
