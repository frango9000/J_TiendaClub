package tiendaclub.data.framework.index;

import tiendaclub.model.models.abstracts.Persistible;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public abstract class SetMultiMapIndex<K, V extends Persistible> extends AbstractIndex<K, V> {

    protected HashMap<K, HashMap<Integer, V>> index;

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


    public HashMap<Integer, V> getKeyMap(K key) {
        return index.get(key);
    }

    public HashMap<K, HashMap<Integer, V>> getIndex() {
        return index;
    }

    public Collection<V> getKeyValues(K key) {
        return index.get(key).values();
    }

    public Set<K> getKeys() {
        return index.keySet();
    }
}
