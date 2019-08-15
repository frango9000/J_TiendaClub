package tiendaclub.data.framework.index;

import com.google.common.collect.MultimapBuilder;
import java.util.Set;
import tiendaclub.data.framework.datasource.DataSource;
import tiendaclub.model.models.abstracts.Activable;

public class SetMultiMapIndexBoolean<T extends Activable> extends SetMultiMapIndex<Boolean, T> {

    //protected SetMultimap<Boolean, T> index;

    public SetMultiMapIndexBoolean(DataSource<T> dataSource) {
        this.dataSource = dataSource;
        this.index = MultimapBuilder.SetMultimapBuilder.hashKeys(2).hashSetValues().build();
    }

    @Override
    public void index(T value) {
        index.put(value.isActivo(), value);
    }

    @Override
    public void reindex(T value) {
        index.remove(!value.isActivo(), value);
        index(value);
    }

    @Override
    public void deindex(T value) {
        index.remove(value.isActivo(), value);
    }

    public Set<T> getActive(boolean active) {
        return getKeyValues(active);
    }

    public Set<T> getActiveCache(boolean active) {
        return getCacheKeyValues(active);
    }
}
