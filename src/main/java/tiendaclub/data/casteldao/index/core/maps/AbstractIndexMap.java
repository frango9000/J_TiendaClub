package tiendaclub.data.casteldao.index.core.maps;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiConsumer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class AbstractIndexMap<K, V> implements IIndexMap<K, V> {

    protected Map<K, V> map;


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
        return key != null && map.containsKey(key);
    }

    @Override
    public boolean containsValue(@Nullable Object value) {
        return value != null && map.containsValue(value);
    }

    @SuppressWarnings("unchecked")//TODO check casting and contains verification
    @Override
    public boolean containsEntry(@Nullable Object key, @Nullable Object value) {
        return key != null && value != null
               && map.entrySet().contains(new AbstractMap.SimpleEntry<K, V>((K) key, (V) value));
    }

    @Override
    public boolean put(@Nullable K key, @Nullable V value) {
        return key != null && value != null && map.putIfAbsent(key, value) == null;
    }

    @SuppressWarnings("unchecked")//TODO check casting
    @Override
    public boolean remove(@Nullable Object key, @Nullable Object value) {
        return key != null && value != null && map.remove(key, value);
    }

    @Override
    public boolean remove(@Nullable K key) {
        return map.remove(key) != null;
    }

    @Override
    public boolean putAll(@Nullable K key, @NonNull Iterable<? extends V> values) {
        if (key == null) {
            return false;
        }
        int size = size();
        for (V value : values) {
            map.putIfAbsent(key, value);
            break;//just add the 1st loosing the rest of data CAUTION
        }
        return size == size();
    }

    @Override
    public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
        int size = size();
        multimap.asMap().forEach((key, values) -> {
            for (V value : values) {
                map.putIfAbsent(key, value);
                break;//just add the 1st loosing the rest of data CAUTION
            }
        });
        return size == size();
    }

    @Override
    public Set<V> replaceValues(@Nullable K key, @NonNull Iterable<? extends V> values) {
        if (key != null && (containsKey(key))) {
            V ret = map.remove(key);
            putAll(key, values);
            return Set.of(ret);
        } else {
            return Set.of();
        }
    }

    @Override
    public Set<V> removeAll(@Nullable Object key) {
        if (key != null && containsKey(key)) {
            return Set.of(map.remove(key));
        } else {
            return Set.of();
        }
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<V> get(@Nullable K key) {
        if (key != null && containsKey(key)) {
            return Set.of(map.get(key));
        } else {
            return Set.of();
        }
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Multiset<K> keys() {
        throw new UnsupportedOperationException("Unsuported Operation");
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entries() {
        return map.entrySet();
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        map.forEach(action);
    }

    @Override
    public Map<K, Collection<V>> asMap() {
        Map<K, Collection<V>> returnMap = Maps.newHashMap();
        returnMap.forEach((key, v) -> {
            returnMap.put(key, get(key));
        });
        return returnMap;
    }

    @Override
    public V getValue(@Nullable K key) {
        return map.get(key);
    }
}
