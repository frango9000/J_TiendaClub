package app.data.casteldao.index.core.maps;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multiset;
import com.google.common.collect.SetMultimap;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiConsumer;
import org.checkerframework.checker.nullness.qual.Nullable;

public class SetIndexMultimap<K, V> implements IIndexMap<K, V> {

    private SetMultimap<K, V> map;

    public SetIndexMultimap() {
        this.map = MultimapBuilder.SetMultimapBuilder.hashKeys().hashSetValues().build();
    }

    public SetIndexMultimap(int keysSize) {
        this.map = MultimapBuilder.SetMultimapBuilder.hashKeys(keysSize).hashSetValues().build();
    }

    @Override
    public Set<V> get(@Nullable K key) {
        return map.get(key);
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Multiset<K> keys() {
        return map.keys();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<V> removeAll(@Nullable Object key) {
        return map.removeAll(key);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(@Nullable Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(@Nullable Object value) {
        return map.containsValue(value);
    }

    @Override
    public boolean containsEntry(@Nullable Object key, @Nullable Object value) {
        return map.containsEntry(key, value);
    }

    @Override
    public boolean put(@Nullable K key, @Nullable V value) {
        return map.put(key, value);
    }

    @Override
    public boolean remove(@Nullable Object key, @Nullable Object value) {
        return map.remove(key, value);
    }

    @Override
    public boolean remove(@Nullable K key) {
        int size = map.size();
        map.removeAll(key);
        return size == map.size();
    }

    @Override
    public boolean putAll(@Nullable K key, Iterable<? extends V> values) {
        return map.putAll(key, values);
    }

    @Override
    public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
        return map.putAll(multimap);
    }

    @Override
    public Set<V> replaceValues(K key, Iterable<? extends V> values) {
        return map.replaceValues(key, values);
    }

    @Override
    public Set<Entry<K, V>> entries() {
        return map.entries();
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        map.forEach(action);
    }

    @Override
    public Map<K, Collection<V>> asMap() {
        return map.asMap();
    }

    @Override
    public V getValue(@Nullable K key) {
        Set<V> values = get(key);
        if (values.size() > 0) {
            Iterator<V> iterator = get(key).iterator();
            if (iterator.hasNext()) {
                return iterator.next();
            }
        }
        return null;
    }
}
