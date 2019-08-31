package app.data.casteldao.index.core;

import app.data.casteldao.DataSource;
import app.data.casteldao.SessionDB;
import app.data.casteldao.daomodel.IPersistible;
import app.data.casteldao.index.core.maps.SetIndexMultimap;
import java.util.Set;
import java.util.function.Function;

public abstract class SetMultiMapIndex<K, V extends IPersistible> extends AbstractIndex<K, V> {

    public SetMultiMapIndex(DataSource<V> dataSource, String indexColumnName, Function<V, K> keyValueFunction) {
        super(dataSource, indexColumnName, keyValueFunction);
        this.index = new SetIndexMultimap<K, V>();
    }

    @Override
    public Set<V> getKeyValues(K key) {
        if (!cacheContainsKey(key))
            dataSource.querySome(indexColumnName, key.toString());
        else
            dataSource.querySome(getCachedIds(key), false);
        return getCacheKeyValues(key);
    }

    @Override
    public Set<V> getKeyValues(Set<K> keys) {
        if (keys.size() > 0) {
            SessionDB.setAutoclose(false);
            try {
                for (K key : keys) {
                    if (!cacheContainsKey(key))
                        dataSource.querySome(indexColumnName, key.toString());
                    else
                        dataSource.querySome(getCachedIds(key), false);
                }
            } finally {
                SessionDB.setAutoclose(true);
            }
        }
        return getCacheKeyValues(keys);
    }
}
