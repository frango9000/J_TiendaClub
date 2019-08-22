package tiendaclub.data.framework.index;

import tiendaclub.data.framework.DataSource;
import tiendaclub.data.framework.index.core.SimpleMapIndex;
import tiendaclub.model.models.core.IPersistible;

public class UniqueIndexId<V extends IPersistible> extends SimpleMapIndex<Integer, V> {

    public UniqueIndexId(DataSource<V> dataSource) {
        super(dataSource, "id", IPersistible::getId);
    }

    @Override
    public Integer indexKey(V value) {
        return value.getId();
    }

    @Override
    public void reindex(V value) {
        deindex(value);
        index(value);
    }

    @Override
    public void deindex(int idValue) {
        index.remove(idValue);
    }

    @Override
    public void deindex(V value) {
        this.deindex(value.getId());
    }
}
