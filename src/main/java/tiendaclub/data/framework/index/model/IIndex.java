package tiendaclub.data.framework.index.model;

import java.util.Optional;
import java.util.Set;

public interface IIndex<K, V> {

    K indexKey(V value);

    void index(V value);

    void deindex(int key);

    void deindex(V value);

    void reindex(V value);

    Set<K> getKeys();

    V getValue(K key);

    Optional<V> getValueOptional(K key);

    Set<V> getValues();

    Set<V> getKeyValues(K key);

    Set<V> getKeyValues(Set<K> keys);

    V getCacheValue(K key);

    Optional<V> getCacheValueOptional(K key);

    Set<K> getCacheKeys();

    Set<V> getCacheValues();

    Set<V> getCacheKeyValues(K key);

    Set<V> getCacheKeyValues(Set<K> keys);

    boolean cacheContainsKey(K key);

    boolean cacheContainsValue(V value);

}
