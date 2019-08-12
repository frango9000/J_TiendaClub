package tiendaclub.data.framework.dao;

import tiendaclub.data.framework.index.MultiMapIndex;
import tiendaclub.model.models.abstracts.Activable;

import java.util.Collection;
import java.util.HashMap;

public class ActivableDao<T extends Activable> extends IdentifiableDao<T> {


    private MultiMapIndex<Boolean, T> activeIndex = new MultiMapIndex<>() {
        {
            INDEX_COL_NAME = "activo";
            index.put(true, new HashMap<>());
            index.put(false, new HashMap<>());
        }

        @Override
        public void index(T objectT) {
            index.get(objectT.isActivo()).put(objectT.getId(), objectT);
        }

        @Override
        public void deindex(T objectT) {
            index.get(objectT.isActivo()).remove(objectT.getId());
        }

        @Override
        public void reindex(T objectT) {
            index.get(!objectT.isActivo()).remove(objectT.getId());
            index(objectT);
        }
    };

    public ActivableDao(String TABLE_NAME) {
        super(TABLE_NAME);
        indexes.add(activeIndex);
    }

    public Collection<T> getActive(boolean active) {
        return activeIndex.getIndexList(active);
    }
}
