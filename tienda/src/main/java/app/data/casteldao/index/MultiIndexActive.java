package app.data.casteldao.index;

import app.data.casteldao.DataSource;
import app.data.casteldao.daomodel.Activable;
import app.data.casteldao.index.core.SetMultiMapIndex;
import app.data.casteldao.index.core.maps.SetIndexMultimap;
import java.util.Set;

public class MultiIndexActive<V extends Activable> extends SetMultiMapIndex<Boolean, V> {

    public MultiIndexActive(DataSource<V> dataSource) {
        super(dataSource, "activo", Activable::isActivo);
        this.index = new SetIndexMultimap<>(2);
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
