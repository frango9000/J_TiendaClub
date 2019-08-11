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
        public void index(T objectV) {
            index.get(objectV.isActivo()).put(objectV.getId(), objectV);
        }

        @Override
        public void deindex(T objectT) {
            index.get(objectT.isActivo()).remove(objectT.getId());
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
