package tiendaclub.data.framework.index;

import java.util.Set;
import tiendaclub.data.framework.datasource.DataSource;
import tiendaclub.data.framework.index.maps.IndexSetMultimap;
import tiendaclub.data.framework.index.model.SetMultiMapIndex;
import tiendaclub.model.models.abstracts.Activable;

public class IndexActive<V extends Activable> extends SetMultiMapIndex<Boolean, V> {

    public IndexActive(DataSource<V> dataSource) {
        this.index = new IndexSetMultimap<>(2);
        this.dataSource = dataSource;
    }

    @Override
    public Boolean indexKey(V value) {
        return value.isActivo();
    }

    @Override
    public void deindex(int id) {
        index.keySet().forEach(aBoolean -> {
            index.get(aBoolean).forEach(t -> {
                if (t.getId() == id) {
                    index.remove(aBoolean, t);
                }
            });
        });
    }

    @Override
    public void reindex(V value) {
        index.remove(!value.isActivo(), value);
        index(value);
    }

    public Set<V> getActive(boolean active) {
        return getKeyValues(active);
    }

    public Set<V> getActiveCache(boolean active) {
        return getCacheKeyValues(active);
    }
}
