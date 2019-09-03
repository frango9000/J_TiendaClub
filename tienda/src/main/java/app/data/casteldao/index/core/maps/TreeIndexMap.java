package app.data.casteldao.index.core.maps;


import app.data.casteldao.model.IEntity;
import com.google.common.collect.Maps;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.BiFunction;

public class TreeIndexMap<K extends Comparable, E extends IEntity<?>> extends AbstractIndexMap<K, E> {


    public TreeIndexMap() {
        this.map = Maps.newTreeMap();
    }

    public TreeMap<K, E> getMap() {
        return (TreeMap<K, E>) map;
    }

    public Comparator<? super K> comparator() {
        return getMap().comparator();
    }

    public K firstKey() {
        return getMap().firstKey();
    }

    public K lastKey() {
        return getMap().lastKey();
    }

    public void putAll(Map<? extends K, ? extends E> map) {
        getMap().putAll(map);
    }

    public Entry<K, E> firstEntry() {
        return getMap().firstEntry();
    }

    public Entry<K, E> lastEntry() {
        return getMap().lastEntry();
    }

    public Entry<K, E> pollFirstEntry() {
        return getMap().pollFirstEntry();
    }

    public Entry<K, E> pollLastEntry() {
        return getMap().pollLastEntry();
    }

    public Entry<K, E> lowerEntry(K key) {
        return getMap().lowerEntry(key);
    }

    public K lowerKey(K key) {
        return getMap().lowerKey(key);
    }

    public Entry<K, E> floorEntry(K key) {
        return getMap().floorEntry(key);
    }

    public K floorKey(K key) {
        return getMap().floorKey(key);
    }

    public Entry<K, E> ceilingEntry(K key) {
        return getMap().ceilingEntry(key);
    }

    public K ceilingKey(K key) {
        return getMap().ceilingKey(key);
    }

    public Entry<K, E> higherEntry(K key) {
        return getMap().higherEntry(key);
    }

    public K higherKey(K key) {
        return getMap().higherKey(key);
    }

    public NavigableSet<K> navigableKeySet() {
        return getMap().navigableKeySet();
    }

    public NavigableSet<K> descendingKeySet() {
        return getMap().descendingKeySet();
    }

    public Set<Entry<K, E>> entrySet() {
        return getMap().entrySet();
    }

    public NavigableMap<K, E> descendingMap() {
        return getMap().descendingMap();
    }

    public NavigableMap<K, E> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
        return getMap().subMap(fromKey, fromInclusive, toKey, toInclusive);
    }

    public NavigableMap<K, E> headMap(K toKey, boolean inclusive) {
        return getMap().headMap(toKey, inclusive);
    }

    public NavigableMap<K, E> tailMap(K fromKey, boolean inclusive) {
        return getMap().tailMap(fromKey, inclusive);
    }

    public SortedMap<K, E> subMap(K fromKey, K toKey) {
        return getMap().subMap(fromKey, toKey);
    }

    public SortedMap<K, E> headMap(K toKey) {
        return getMap().headMap(toKey);
    }

    public SortedMap<K, E> tailMap(K fromKey) {
        return getMap().tailMap(fromKey);
    }

    public boolean replace(K key, E oldValue, E newValue) {
        return getMap().replace(key, oldValue, newValue);
    }

    public E replace(K key, E value) {
        return getMap().replace(key, value);
    }

    public void replaceAll(BiFunction<? super K, ? super E, ? extends E> function) {
        getMap().replaceAll(function);
    }
}
