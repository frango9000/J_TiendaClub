package tiendaclub.data.framework.index;

import java.util.Collection;
import java.util.HashMap;

public abstract class MultiMapIndex<K, V> extends AbstractIndex<K, V> {

    protected HashMap<K, HashMap<Integer, V>> index = new HashMap<>();

    @Override
    public abstract void index(V objectV);

    @Override
    public void deindex(int id) {
        index.forEach((e, f) -> f.remove(id));
    }

    @Override
    public abstract void deindex(V objectT);

    @Override
    public abstract void reindex(V objectT);

    @Override
    public HashMap<Integer, V> getIndexMap(K key) {
        return index.get(key);
    }

    @Override
    public HashMap<Integer, V> getIndexMap() {
        return null;
    }

    @Override
    public Collection<V> getIndexList(K key) {
        return index.get(key).values();
    }

    @Override
    public Collection<V> getIndexList() {
        return null;
    }
}
