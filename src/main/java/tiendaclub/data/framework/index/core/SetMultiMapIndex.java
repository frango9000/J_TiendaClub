package tiendaclub.data.framework.index.core;

import java.util.Set;
import java.util.function.Function;
import tiendaclub.data.framework.DataSource;
import tiendaclub.data.framework.SessionDB;
import tiendaclub.data.framework.index.core.maps.SetIndexMultimap;
import tiendaclub.model.models.core.IPersistible;

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
