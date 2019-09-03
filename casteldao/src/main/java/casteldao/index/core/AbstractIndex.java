package casteldao.index.core;

import casteldao.GenericDao;
import casteldao.index.core.maps.IIndexMap;
import casteldao.model.IEntity;
import com.google.common.collect.Sets;
import java.io.Serializable;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class AbstractIndex<K, E extends IEntity<I>, I extends Serializable> implements IIndex<K, E, I> {

    protected IIndexMap<K, E> index;

    protected GenericDao<I, E> dataSource;

    protected String indexColumnName = "";

    protected Function<E, K> keyValueFunction;

    protected AbstractIndex(@NonNull GenericDao<I, E> dataSource, String indexColumnName, Function<E, K> keyValueFunction) {
        this.dataSource       = dataSource;
        this.indexColumnName  = indexColumnName;
        this.keyValueFunction = keyValueFunction;
    }

    @Override
    public K indexKey(E entity) {
        return keyValueFunction.apply(entity);
    }

    @Override
    public void index(E entity) {
        index.put(indexKey(entity), entity);
    }

    @Override
    public void reindex(E entity) {
        index.entries().removeIf(kvEntry -> kvEntry.getValue() == entity);
        index(entity);
    }

    @Override
    public void deindex(E entity) {
        index.remove(indexKey(entity), entity);
    }

    @Override
    public void deindex(I key) {
        index.entries().removeIf(kvEntry -> kvEntry.getValue().getId().equals(key));
    }

    @Override
    public Set<K> getKeys() {
        dataSource.queryAll();
        return getCacheKeys();
    }

    @Override
    public Set<E> getValues() {
        dataSource.queryAll();
        return getCacheValues();
    }

    @Override
    public Set<E> getKeyValues(K key) {
        dataSource.querySome(indexColumnName, key.toString());
        return getCacheKeyValues(key);
    }

    @Override
    public Set<E> getKeyValues(Set<K> keys) {
        dataSource.querySome(indexColumnName, keys);
        return getCacheKeyValues(keys);
    }

    @Override
    public Set<K> getCacheKeys() {
        return index.keySet();
    }

    @Override
    public Set<E> getCacheValues() {
        return Sets.newHashSet(index.values());
    }

    @Override
    public Set<E> getCacheKeyValues(K key) {
        return index.get(key);
    }

    @Override
    public Set<E> getCacheKeyValues(Set<K> keys) {
        Set<E> values = Sets.newHashSet();
        keys.forEach(e -> values.addAll(index.get(e)));
        return values;
    }

    @Override
    public boolean cacheContainsKey(K key) {
        return index.containsKey(key);
    }

    @Override
    public boolean cacheContainsValue(E entity) {
        return index.containsValue(entity);
    }

    @Override
    public E getValue(K key) {
        dataSource.querySome(indexColumnName, key.toString());
        return getCacheValue(key);
    }

    @Override
    public E getCacheValue(K key) {
        return index.getValue(key);
    }

    @Override
    public Optional<E> getValueOptional(K key) {
        if (!cacheContainsKey(key)) {
            getKeyValues(key);
        }
        return getCacheValueOptional(key);
    }

    @Override
    public Optional<E> getCacheValueOptional(K key) {
        if (cacheContainsKey(key)) {
            return Optional.of(getCacheValue(key));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public int getCacheKeySize() {
        return getCacheKeys().size();
    }

    @Override
    public int getCacheKeySize(K key) {
        return getCacheKeyValues(key).size();
    }

    @Override
    public int getKeySize() {
        return dataSource.queryOneColumn(dataSource.getIdColName(), indexColumnName, "-1", false).size();
    }

    @Override
    public int getKeySize(K key) {
        return dataSource.queryOneColumn(dataSource.getIdColName(), indexColumnName, key.toString()).size();
    }

    @Override
    public Set<I> getIds() {
        return dataSource.getAllIds();
    }

    @Override
    public Set<I> getIds(K key) {
        return dataSource.getOnlyIds(indexColumnName, Collections.singletonList(key), true);
    }

    @Override
    public Set<I> getIds(Set<K> keys) {
        return dataSource.getOnlyIds(indexColumnName, keys, true);
    }

    @Override
    public Set<I> getCachedIds() {
        return getCacheValues().stream().map(E::getId).collect(Collectors.toSet());
    }

    @Override
    public Set<I> getCachedIds(K key) {
        return getCacheKeyValues(key).stream().map(E::getId).collect(Collectors.toSet());
    }

    @Override
    public Set<I> getCachedIds(Set<K> keys) {
        return getCacheKeyValues(keys).stream().map(E::getId).collect(Collectors.toSet());
    }


}
