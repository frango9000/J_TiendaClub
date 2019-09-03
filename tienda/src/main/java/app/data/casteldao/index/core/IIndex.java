package app.data.casteldao.index.core;

import app.data.casteldao.model.IEntity;
import java.io.Serializable;
import java.util.Optional;
import java.util.Set;

public interface IIndex<K, E extends IEntity<I>, I extends Serializable> {

    K indexKey(E entity);

    void index(E entity);

    void deindex(I key);

    void deindex(E entity);

    void reindex(E entity);

    Set<K> getKeys();

    E getValue(K key);

    Optional<E> getValueOptional(K key);

    Set<E> getValues();

    Set<E> getKeyValues(K key);

    Set<E> getKeyValues(Set<K> keys);

    E getCacheValue(K key);

    Optional<E> getCacheValueOptional(K key);

    Set<K> getCacheKeys();

    Set<E> getCacheValues();

    Set<E> getCacheKeyValues(K key);

    Set<E> getCacheKeyValues(Set<K> keys);

    boolean cacheContainsKey(K key);

    boolean cacheContainsValue(E entity);


    int getCacheKeySize();

    int getCacheKeySize(K key);

    int getKeySize();

    int getKeySize(K key);

    Set<I> getIds();

    Set<I> getIds(K key);

    Set<I> getIds(Set<K> keys);

    Set<I> getCachedIds();

    Set<I> getCachedIds(K key);

    Set<I> getCachedIds(Set<K> keys);

}
