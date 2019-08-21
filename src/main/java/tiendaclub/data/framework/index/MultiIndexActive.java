package tiendaclub.data.framework.index;

import java.util.Set;
import tiendaclub.data.framework.DataSource;
import tiendaclub.data.framework.index.core.SetMultiMapIndex;
import tiendaclub.data.framework.index.core.maps.IndexSetMultimap;
import tiendaclub.model.models.core.Activable;

public class MultiIndexActive<V extends Activable> extends SetMultiMapIndex<Boolean, V> {

    public MultiIndexActive(DataSource<V> dataSource) {
        super(dataSource, "activo", Activable::isActivo);
        this.index = new IndexSetMultimap<>(2);
    }

    @Override
    public Boolean indexKey(V value) {
        return value.isActivo();
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
