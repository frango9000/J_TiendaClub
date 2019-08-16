package tiendaclub.data.framework.index.model;

import com.google.common.collect.Sets;
import java.util.Optional;
import java.util.Set;
import tiendaclub.data.framework.datasource.DataSource;
import tiendaclub.data.framework.index.maps.IIndexMap;

public abstract class AbstractIndex<K, V> implements IIndex<K, V> {

    protected IIndexMap<K, V> index;

    protected DataSource<?> dataSource;

    protected String INDEX_COL_NAME;


    @Override
    public abstract K indexKey(V value);

    @Override
    public void index(V value) {
        index.put(indexKey(value), value);
    }

    @Override
    public void reindex(V value) {
        deindex(value);
        index(value);
    }

    @Override
    public void deindex(V value) {
        index.remove(indexKey(value), value);
    }

    @Override
    public abstract void deindex(int id);

    @Override
    public Set<K> getKeys() {
        dataSource.queryAll();
        return getCacheKeys();
    }

    @Override
    public Set<V> getValues() {
        dataSource.queryAll();
        return getCacheValues();
    }

    @Override
    public Set<V> getKeyValues(K key) {
        dataSource.querySome(INDEX_COL_NAME, key);
        return getCacheKeyValues(key);
    }

    @Override
    public Set<V> getKeyValues(Set<K> keys) {
        dataSource.querySome(INDEX_COL_NAME, keys);
        return getCacheKeyValues(keys);
    }

    @Override
    public Set<K> getCacheKeys() {
        return index.keySet();
    }

    @Override
    public Set<V> getCacheValues() {
        return Sets.newHashSet(index.values());
    }

    @Override
    public Set<V> getCacheKeyValues(K key) {
        return index.get(key);
    }

    @Override
    public Set<V> getCacheKeyValues(Set<K> keys) {
        Set<V> values = Sets.newHashSet();
        keys.forEach(e -> values.addAll(index.get(e)));
        return values;
    }

    @Override
    public boolean cacheContainsKey(K key) {
        return index.containsKey(key);
    }

    @Override
    public boolean cacheContainsValue(V value) {
        return index.containsValue(value);
    }

    @Override
    public V getValue(K key) {
        dataSource.querySome(INDEX_COL_NAME, key);
        return getCacheValue(key);
    }

    @Override
    public V getCacheValue(K key) {
        return index.getValue(key);
    }

    @Override
    public Optional<V> getValueOptional(K key) {
        if (!cacheContainsKey(key)) {
            getKeyValues(key);
        }
        return getCacheValueOptional(key);
    }

    @Override
    public Optional<V> getCacheValueOptional(K key) {
        if (cacheContainsKey(key)) {
            return Optional.of(getCacheValue(key));
        } else {
            return Optional.empty();
        }
    }

}
