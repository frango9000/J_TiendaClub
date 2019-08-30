package tiendaclub.data.casteldao.index.core;

import java.util.Set;
import java.util.function.Function;
import tiendaclub.data.casteldao.DataSource;
import tiendaclub.data.casteldao.SessionDB;
import tiendaclub.data.casteldao.daomodel.IPersistible;
import tiendaclub.data.casteldao.index.core.maps.SetIndexMultimap;

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
