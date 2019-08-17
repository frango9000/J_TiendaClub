package tiendaclub.data.framework.index.model;

import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.Set;
import tiendaclub.data.framework.index.maps.IndexHashMap;
import tiendaclub.model.models.abstracts.IPersistible;

public abstract class SimpleMapIndex<K, V extends IPersistible> extends AbstractIndex<K, V> {

    {
        this.index = new IndexHashMap<K, V>();
    }

    @Override
    public void deindex(int idValue) {
        index.entries().removeIf(kvEntry -> kvEntry.getValue().getId() == idValue);
    }

    @Override
    public Set<V> getKeyValues(K key) {
        if (!cacheContainsKey(key)) {
            dataSource.query(INDEX_COL_NAME, key);
        }
        return getCacheKeyValues(key);
    }

    @Override
    public Set<V> getKeyValues(Set<K> keys) {
        if (keys.size() > 0) {
            HashSet<K> keysToQuery = Sets.newHashSet();
            for (K key : keys) {
                if (!cacheContainsKey(key)) {
                    keysToQuery.add(key);
                }
            }
            if (keysToQuery.size() > 0) {
                dataSource.querySome(INDEX_COL_NAME, keys);
            }
        }
        return getCacheKeyValues(keys);
    }

    @Override
    public V getValue(K key) {
        if (!cacheContainsKey(key)) {
            dataSource.query(INDEX_COL_NAME, key);
        }
        return getCacheValue(key);
    }
}
