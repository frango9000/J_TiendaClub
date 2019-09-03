package app.data.casteldao.index.core;

import app.data.casteldao.GenericDao;
import app.data.casteldao.index.core.maps.HashIndexMap;
import app.data.casteldao.model.IEntity;
import com.google.common.collect.Sets;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class SimpleMapIndex<K, E extends IEntity<I>, I extends Serializable> extends AbstractIndex<K, E, I> {

    public SimpleMapIndex(GenericDao<I, E> dataSource, String indexColumnName, Function<E, K> keyValueFunction) {
        super(dataSource, indexColumnName, keyValueFunction);
        this.index = new HashIndexMap<K, E>();
    }

    @Override
    public Set<E> getKeyValues(K key) {
        if (!cacheContainsKey(key)) {
            dataSource.query(indexColumnName, key);
        }
        return getCacheKeyValues(key);
    }

    @Override
    public Set<E> getKeyValues(Set<K> keys) {
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
    public E getValue(K key) {
        if (!cacheContainsKey(key)) {
            dataSource.query(indexColumnName, key);
        }
        return getCacheValue(key);
    }
}
