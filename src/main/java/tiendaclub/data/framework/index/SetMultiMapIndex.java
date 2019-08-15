package tiendaclub.data.framework.index;

import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import java.util.Optional;
import java.util.Set;
import tiendaclub.model.models.abstracts.Persistible;

public abstract class SetMultiMapIndex<K, V extends Persistible> extends AbstractIndex<K, V> {

    protected SetMultimap<K, V> index;// = MultimapBuilder.SetMultimapBuilder.hashKeys().hashSetValues().build();

    @Override
    public abstract void index(V value);

    @Override
    public abstract void reindex(V value);

    @Override
    public void deindex(int id) {
        index.keySet().forEach(aBoolean -> {
            index.get(aBoolean).forEach(t -> {
                if (t.getId() == id) {
                    index.remove(aBoolean, t);
                }
            });
        });
    }

    @Override
    public abstract void deindex(V value);

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
        dataSource.querySome(INDEX_COL_NAME, key.toString());
        return getCacheKeyValues(key);
    }

    @Override
    public Set<V> getKeyValues(Set<K> keys) {
        Set<String> strings = Sets.newHashSetWithExpectedSize(keys.size());
        keys.forEach(e -> strings.add(e.toString()));
        dataSource.querySome(INDEX_COL_NAME, strings);
        return getCacheKeyValues(keys);
    }

    @Override
    public Set<K> getCacheKeys() {
        return index.keySet();
    }

    @Override
    public Set<V> getCacheValues() {
        Set<V> values = Sets.newHashSet();
        index.keySet().forEach(e -> values.addAll(index.get(e)));
        return values;
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
        if (!cacheContainsKey(key)) {
            getKeyValues(key);
        }
        return getCacheValue(key);
    }

    @Override
    public V getCacheValue(K key) {
        if (getCacheKeyValues(key).size() > 0) {
            return getCacheKeyValues(key).iterator().next();
        }
        return null;
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
            return Optional.of(index.get(key).iterator().next());
        } else {
            return Optional.empty();
        }
    }
}
