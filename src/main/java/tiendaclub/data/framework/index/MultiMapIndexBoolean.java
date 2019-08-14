package tiendaclub.data.framework.index;

import tiendaclub.data.framework.datasource.DataSource;
import tiendaclub.model.models.abstracts.Activable;

import java.util.Collection;
import java.util.HashMap;

public class MultiMapIndexBoolean<T extends Activable> extends MultiMapIndex<Boolean, T> {


    public MultiMapIndexBoolean(DataSource<T> dataSource) {
        this.dataSource = dataSource;
        this.index = new HashMap<Boolean, HashMap<Integer, T>>();
        index.put(true, new HashMap<Integer, T>());
        index.put(false, new HashMap<Integer, T>());
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

    public Collection<T> getActive(boolean active) {
        dataSource.querySome("activo", Boolean.toString(active));
        return getCacheActive(active);
    }

    public Collection<T> getCacheActive(boolean active) {
        return index.get(active).values();
    }


}
