package tiendaclub.data.framework.index.core;

import java.util.function.Function;
import tiendaclub.data.framework.DataSource;
import tiendaclub.data.framework.index.core.maps.IndexSetMultimap;
import tiendaclub.model.models.core.IPersistible;

public abstract class SetMultiMapIndex<K, V extends IPersistible> extends AbstractIndex<K, V> {

    public SetMultiMapIndex(DataSource<V> dataSource, String indexColumnName, Function<V, K> keyValueFunction) {
        super(dataSource, indexColumnName, keyValueFunction);
        this.index = new IndexSetMultimap<K, V>();
    }


    @Override
    public void deindex(int idValue) {
        index.keySet().forEach(key -> index.get(key).forEach(value -> {
            if (value.getId() == idValue) {
                index.remove(key, value);
            }
        }));
    }
}
