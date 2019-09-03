package casteldao.index;

import casteldao.GenericDao;
import casteldao.index.core.SetMultiMapIndex;
import casteldao.model.IEntity;
import com.google.common.collect.Sets;
import java.util.Set;
import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.NonNull;

public class SetMultiMapIndexEntityImpl<K extends IEntity<Integer>, E extends IEntity<Integer>> extends SetMultiMapIndex<Integer, E, Integer> {


    public SetMultiMapIndexEntityImpl(GenericDao<Integer, E> dataSource, String indexColumnName, Function<E, Integer> keyValueFunction) {
        super(dataSource, indexColumnName, keyValueFunction);
    }

    public Set<E> getKeyValues(@NonNull K refKey) {
        return super.getKeyValues(refKey.getId());
    }

    public Set<E> getKeysValues(@NonNull Set<K> refKeys) {
        Set<Integer> ids = Sets.newHashSetWithExpectedSize(refKeys.size());
        refKeys.forEach(k -> ids.add(k.getId()));
        return super.getKeyValues(ids);
    }

    public Set<E> getCacheKeyValues(@NonNull K refKey) {
        return super.getCacheKeyValues(refKey.getId());
    }

    public Set<E> getCacheKeysValues(Set<K> refKeys) {
        Set<Integer> ids = Sets.newHashSetWithExpectedSize(refKeys.size());
        refKeys.forEach(k -> ids.add(k.getId()));
        return super.getCacheKeyValues(ids);
    }

    public boolean cacheContainsKey(@NonNull K refKey) {
        return super.cacheContainsKey(refKey.getId());
    }

    public E getValue(@NonNull K refKey) {
        return super.getValue(refKey.getId());
    }

    public E getCacheValue(@NonNull K refKey) {
        return super.getCacheValue(refKey.getId());
    }
}