package casteldao.index.core;

import casteldao.GenericDao;
import casteldao.SessionDB;
import casteldao.index.core.maps.SetIndexMultimap;
import casteldao.model.IEntity;
import java.io.Serializable;
import java.util.Set;
import java.util.function.Function;

public abstract class SetMultiMapIndex<K, E extends IEntity<I>, I extends Serializable> extends AbstractIndex<K, E, I> {

    public SetMultiMapIndex(GenericDao<I, E> dataSource, String indexColumnName, Function<E, K> keyValueFunction) {
        super(dataSource, indexColumnName, keyValueFunction);
        this.index = new SetIndexMultimap<K, E>();
    }

    @Override
    public Set<E> getKeyValues(K key) {
        if (!cacheContainsKey(key))
            dataSource.querySome(indexColumnName, key.toString());
        else
            dataSource.querySome(getCachedIds(key), false);
        return getCacheKeyValues(key);
    }

    @Override
    public Set<E> getKeyValues(Set<K> keys) {
        if (keys.size() > 0) {
            SessionDB.getSessionDB().setAutoclose(false);
            try {
                for (K key : keys) {
                    if (!cacheContainsKey(key))
                        dataSource.querySome(indexColumnName, key.toString());
                    else
                        dataSource.querySome(getCachedIds(key), false);
                }
            } finally {
                SessionDB.getSessionDB().setAutoclose(true);
            }
        }
        return getCacheKeyValues(keys);
    }
}
