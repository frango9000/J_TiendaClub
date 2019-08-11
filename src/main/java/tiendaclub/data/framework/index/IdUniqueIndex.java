package tiendaclub.data.framework.index;

import tiendaclub.model.models.abstracts.Identifiable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class IdUniqueIndex<V extends Identifiable> extends AbstractIndex<Integer, V> {
    protected HashMap<Integer, V> index = new HashMap<Integer, V>();

    @Override
    public void index(V objectV) {
        index.putIfAbsent(objectV.getId(), objectV);
    }

    @Override
    public void deindex(int id) {
        index.remove(id);
    }

    @Override
    public void deindex(V objectT) {
        deindex(objectT.getId());
    }

    @Override
    public void reindex(V objectT) {
        deindex(objectT);
        index(objectT);
    }

    @Override
    public HashMap getIndexMap(Integer key) {
        return getIndexMap();
    }

    @Override
    public HashMap<Integer, V> getIndexMap() {
        return index;
    }

    @Override
    public Collection<V> getIndexList() {
        return index.values();
    }

    @Override
    public Collection<V> getIndexList(Integer key) {
        return Collections.singletonList(index.get(key));
    }

}
