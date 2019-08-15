package tiendaclub.data.framework.index;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import tiendaclub.data.framework.datasource.DataSource;
import tiendaclub.model.models.abstracts.Persistible;

public class SimpleIndexId<V extends Persistible> extends AbstractIndex<Integer, V> {

    protected HashMap<Integer, V> index = Maps.newHashMap();

    public SimpleIndexId(DataSource<V> dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void index(V value) {
        index.putIfAbsent(value.getId(), value);
    }

    @Override
    public void deindex(int id) {
        index.remove(id);
    }

    @Override
    public void deindex(V value) {
        deindex(value.getId());
    }

    @Override
    public void reindex(V value) {
        deindex(value);
        index(value);
    }

    @Override
    public Set<Integer> getKeys() {
        dataSource.queryAll();
        return getCacheKeys();
    }

    @Override
    public Set<V> getValues() {
        dataSource.queryAll();
        return getCacheValues();
    }

    @Override
    public Set<V> getKeyValues(Integer key) {
        Set<V> set = Sets.newHashSetWithExpectedSize(1);
        if (key > 0) {
            if (!cacheContainsKey(key)) {
                dataSource.query(key);
            }
            if (cacheContainsKey(key)) {
                set.add(index.get(key));
            }
        }
        return set;
    }

    @Override
    public Set<V> getKeyValues(Set<Integer> keys) {
        Set<V> list = Sets.newHashSetWithExpectedSize(keys.size());
        if (keys.size() > 0) {
            HashSet<Integer> keysToQuery = Sets.newHashSet();
            for (int key : keys) {
                if (!cacheContainsKey(key)) {
                    keysToQuery.add(key);
                }
            }
            if (keysToQuery.size() > 0) {
                dataSource.querySome(keysToQuery);
            }
        }
        return getCacheKeyValues(keys);
    }

    @Override
    public Set<Integer> getCacheKeys() {
        return index.keySet();
    }

    @Override
    public Set<V> getCacheValues() {
        return Sets.newHashSet(index.values());
    }

    @Override
    public Set<V> getCacheKeyValues(Integer key) {
        Set<V> set = Sets.newHashSetWithExpectedSize(1);
        if (cacheContainsKey(key)) {
            set.add(index.get(key));
        }
        return set;
    }

    @Override
    public Set<V> getCacheKeyValues(Set<Integer> keys) {
        Set<V> set = Sets.newHashSetWithExpectedSize(keys.size());
        keys.forEach(key -> {
            if (cacheContainsKey(key)) {
                set.add(index.get(key));
            }
        });
        return set;
    }

    @Override
    public boolean cacheContainsKey(Integer key) {
        return index.containsKey(key);
    }

    @Override
    public boolean cacheContainsValue(V value) {
        return index.containsValue(value);
    }

    @Override
    public V getValue(Integer key) {
        if (!cacheContainsKey(key)) {
            dataSource.query(key);
        }
        return getCacheValue(key);
    }

    @Override
    public V getCacheValue(Integer key) {
        return index.get(key);
    }


    @Override
    public Optional<V> getValueOptional(Integer key) {
        if (!cacheContainsKey(key)) {
            dataSource.query(key);
        }
        return getCacheValueOptional(key);
    }

    @Override
    public Optional<V> getCacheValueOptional(Integer key) {
        if (cacheContainsKey(key)) {
            return Optional.of(index.get(key));
        } else {
            return Optional.empty();
        }
    }

}
