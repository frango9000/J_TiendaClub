package app.data.casteldao.index.core;

import app.data.casteldao.DataSource;
import app.data.casteldao.daomodel.IPersistible;
import app.data.casteldao.index.core.maps.IIndexMap;
import com.google.common.collect.Sets;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class AbstractIndex<K, V extends IPersistible> implements IIndex<K, V> {

    protected IIndexMap<K, V> index;

    protected DataSource<V> dataSource;

    protected String indexColumnName = "";

    protected Function<V, K> keyValueFunction;

    protected AbstractIndex(@NonNull DataSource<V> dataSource, String indexColumnName, Function<V, K> keyValueFunction) {
        this.dataSource       = dataSource;
        this.indexColumnName  = indexColumnName;
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
        index.entries().removeIf(kvEntry -> kvEntry.getValue() == value);
        index(value);
    }

    @Override
    public void deindex(V value) {
        index.remove(indexKey(value), value);
    }

    @Override
    public void deindex(int idValue) {
        index.entries().removeIf(kvEntry -> kvEntry.getValue().getId() == idValue);
    }

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
        dataSource.querySome(indexColumnName, key.toString());
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
        dataSource.querySome(indexColumnName, key.toString());
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

    public int getCacheKeySize() {
        return getCacheKeys().size();
    }

    public int getCacheKeySize(K key) {
        return getCacheKeyValues(key).size();
    }

    public int getKeySize() {
        return dataSource.queryOneColumn(dataSource.getIdColName(), indexColumnName, "-1", false).size();
    }

    public int getKeySize(K key) {
        return dataSource.queryOneColumn(dataSource.getIdColName(), indexColumnName, key.toString()).size();
    }

    public Set<Integer> getIds() {
        return dataSource.queryOneColumn(dataSource.getIdColName(), indexColumnName, "0", false)
                         .stream()
                         .map(Integer::parseInt)
                         .collect(Collectors.toSet());
    }

    public Set<Integer> getIds(K key) {
        return dataSource.queryOneColumn(dataSource.getIdColName(), indexColumnName, key.toString())
                         .stream()
                         .map(Integer::parseInt)
                         .collect(Collectors.toSet());
    }

    public Set<Integer> getIds(Set<K> keys) {
        return dataSource.queryOneColumn(dataSource.getIdColName(), indexColumnName, keys.toString())
                         .stream()
                         .map(Integer::parseInt)
                         .collect(Collectors.toSet());
    }

    public Set<Integer> getCachedIds() {
        return getCacheValues().stream().map(V::getId).collect(Collectors.toSet());
    }

    public Set<Integer> getCachedIds(K key) {
        return getCacheKeyValues(key).stream().map(V::getId).collect(Collectors.toSet());
    }

    public Set<Integer> getCachedIds(Set<K> keys) {
        return getCacheKeyValues(keys).stream().map(V::getId).collect(Collectors.toSet());
    }


}
