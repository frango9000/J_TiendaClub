package app.data.casteldao.index.core.maps;


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

public class TreeIndexMap<K extends Comparable, V> extends AbstractIndexMap<K, V> {


    public TreeIndexMap() {
        this.map = Maps.newTreeMap();
    }

    public TreeMap<K, V> getMap() {
        return (TreeMap<K, V>) map;
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

    public void putAll(Map<? extends K, ? extends V> map) {
        getMap().putAll(map);
    }

    public Entry<K, V> firstEntry() {
        return getMap().firstEntry();
    }

    public Entry<K, V> lastEntry() {
        return getMap().lastEntry();
    }

    public Entry<K, V> pollFirstEntry() {
        return getMap().pollFirstEntry();
    }

    public Entry<K, V> pollLastEntry() {
        return getMap().pollLastEntry();
    }

    public Entry<K, V> lowerEntry(K key) {
        return getMap().lowerEntry(key);
    }

    public K lowerKey(K key) {
        return getMap().lowerKey(key);
    }

    public Entry<K, V> floorEntry(K key) {
        return getMap().floorEntry(key);
    }

    public K floorKey(K key) {
        return getMap().floorKey(key);
    }

    public Entry<K, V> ceilingEntry(K key) {
        return getMap().ceilingEntry(key);
    }

    public K ceilingKey(K key) {
        return getMap().ceilingKey(key);
    }

    public Entry<K, V> higherEntry(K key) {
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

    public Set<Entry<K, V>> entrySet() {
        return getMap().entrySet();
    }

    public NavigableMap<K, V> descendingMap() {
        return getMap().descendingMap();
    }

    public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
        return getMap().subMap(fromKey, fromInclusive, toKey, toInclusive);
    }

    public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
        return getMap().headMap(toKey, inclusive);
    }

    public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
        return getMap().tailMap(fromKey, inclusive);
    }

    public SortedMap<K, V> subMap(K fromKey, K toKey) {
        return getMap().subMap(fromKey, toKey);
    }

    public SortedMap<K, V> headMap(K toKey) {
        return getMap().headMap(toKey);
    }

    public SortedMap<K, V> tailMap(K fromKey) {
        return getMap().tailMap(fromKey);
    }

    public boolean replace(K key, V oldValue, V newValue) {
        return getMap().replace(key, oldValue, newValue);
    }

    public V replace(K key, V value) {
        return getMap().replace(key, value);
    }

    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        getMap().replaceAll(function);
    }
}
