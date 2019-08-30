package tiendaclub.data.casteldao.index;

import tiendaclub.data.casteldao.DataSource;
import tiendaclub.data.casteldao.daomodel.IPersistible;
import tiendaclub.data.casteldao.index.core.SimpleMapIndex;

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
