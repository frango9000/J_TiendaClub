package app.data.casteldao.index;

import app.data.casteldao.DataSource;
import app.data.casteldao.daomodel.IPersistible;
import app.data.casteldao.index.core.SetMultiMapIndex;
import com.google.common.collect.Sets;
import java.util.Set;
import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.NonNull;

public class MultiIndexPersistible<R extends IPersistible, V extends IPersistible> extends SetMultiMapIndex<Integer, V> {

    public MultiIndexPersistible(DataSource<V> dataSource, String indexColumnName,
                                 Function<V, Integer> keyValueFunction) {
        super(dataSource, indexColumnName, keyValueFunction);
    }

    public Set<V> getKeyValues(@NonNull R refKey) {
        return super.getKeyValues(refKey.getId());
    }

    public Set<V> getKeysValues(@NonNull Set<R> refKeys) {
        Set<Integer> ids = Sets.newHashSetWithExpectedSize(refKeys.size());
        refKeys.forEach(k -> ids.add(k.getId()));
        return super.getKeyValues(ids);
    }

    public Set<V> getCacheKeyValues(@NonNull R refKey) {
        return super.getCacheKeyValues(refKey.getId());
    }

    public Set<V> getCacheKeysValues(Set<R> refKeys) {
        Set<Integer> ids = Sets.newHashSetWithExpectedSize(refKeys.size());
        refKeys.forEach(k -> ids.add(k.getId()));
        return super.getCacheKeyValues(ids);
    }

    public boolean cacheContainsKey(@NonNull R refKey) {
        return super.cacheContainsKey(refKey.getId());
    }

    public V getValue(@NonNull R refKey) {
        return super.getValue(refKey.getId());
    }

    public V getCacheValue(@NonNull R refKey) {
        return super.getCacheValue(refKey.getId());
    }
}
