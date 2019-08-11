package tiendaclub.data.framework.index;

import java.util.Collection;
import java.util.HashMap;

public abstract class MultiMapIndex<K, V> extends AbstractIndex<K, V> {

    protected HashMap<K, HashMap<Integer, V>> index = new HashMap<>();

    public abstract void index(V objectV);

    public void deindex(int id) {
        index.forEach((e, f) -> f.remove(id));
    }

    public abstract void deindex(V objectT);

    public void reindex(V objectT) {
        deindex(objectT);
        index(objectT);
    }

    public HashMap<Integer, V> getIndexMap(K key) {
        return index.get(key);
    }

    public HashMap<Integer, V> getIndexMap() {
        return null;
    }

    public Collection<V> getIndexList(K key) {
        return index.get(key).values();
    }

    public Collection<V> getIndexList() {
        return null;
    }
}
