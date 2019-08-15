package tiendaclub.data.framework.index;

import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import tiendaclub.data.framework.datasource.DataSource;

public abstract class AbstractIndex<K, V> {

    protected HashMap<K, V> index;

    protected DataSource<?> dataSource;

    protected String INDEX_COL_NAME;

    public abstract void index(V value);

    public abstract void deindex(int key);

    public abstract void deindex(V value);

    public abstract void reindex(V value);

    public abstract Set<K> getKeys();

    public abstract V getValue(K key);

    public abstract Optional<V> getValueOptional(K key);

    public abstract Set<V> getValues();

    public abstract Set<V> getKeyValues(K key);

    public abstract Set<V> getKeyValues(Set<K> keys);

    public abstract V getCacheValue(K key);

    public abstract Optional<V> getCacheValueOptional(K key);

    public abstract Set<K> getCacheKeys();

    public abstract Set<V> getCacheValues();

    public abstract Set<V> getCacheKeyValues(K key);

    public abstract Set<V> getCacheKeyValues(Set<K> keys);

    public abstract boolean cacheContainsKey(K key);

    public abstract boolean cacheContainsValue(V value);

}
