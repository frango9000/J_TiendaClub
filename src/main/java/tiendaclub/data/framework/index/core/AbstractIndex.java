package tiendaclub.data.framework.index.core;

import com.google.common.collect.Sets;
import java.io.Writer;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import tiendaclub.data.framework.datasource.DataSource;
import tiendaclub.data.framework.index.core.maps.IIndexMap;
import tiendaclub.model.models.core.IPersistible;

public abstract class AbstractIndex<K, V extends IPersistible> implements IIndex<K, V> {

    protected IIndexMap<K, V> index;

    protected DataSource<V> dataSource;

    protected String indexColumnName = "";

    protected Function<V, K> keyValueFunction;

    protected AbstractIndex(DataSource<V> dataSource, String indexColumnName, Function<V, K> keyValueFunction) {
        this.dataSource = dataSource;
        this.indexColumnName = indexColumnName;
        this.keyValueFunction = keyValueFunction;
    }

    @Override
    public K indexKey(V value) {
        return keyValueFunction.apply(value);
    }

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
    public abstract void deindex(int idValue);

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
        dataSource.querySome(indexColumnName, key);
        return getCacheKeyValues(key);
    }

    @Override
    public Set<V> getKeyValues(Set<K> keys) {
        dataSource.querySome(indexColumnName, keys);
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
        dataSource.querySome(indexColumnName, key);
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
        Writer out;

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
