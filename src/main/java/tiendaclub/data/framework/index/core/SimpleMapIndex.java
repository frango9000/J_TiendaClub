package tiendaclub.data.framework.index.core;

import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import tiendaclub.data.framework.DataSource;
import tiendaclub.data.framework.index.core.maps.IndexHashMap;
import tiendaclub.model.models.core.IPersistible;

public class SimpleMapIndex<K, V extends IPersistible> extends AbstractIndex<K, V> {

    public SimpleMapIndex(DataSource<V> dataSource, String indexColumnName, Function<V, K> keyValueFunction) {
        super(dataSource, indexColumnName, keyValueFunction);
        this.index = new IndexHashMap<K, V>();
    }

    @Override
    public Set<V> getKeyValues(K key) {
        if (!cacheContainsKey(key)) {
            dataSource.query(indexColumnName, key);
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
                dataSource.querySome(indexColumnName, keys);
            }
        }
        return getCacheKeyValues(keys);
    }

    @Override
    public V getValue(K key) {
        if (!cacheContainsKey(key)) {
            dataSource.query(indexColumnName, key);
        }
        return getCacheValue(key);
    }
}
