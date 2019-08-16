package tiendaclub.data.framework.index;

import tiendaclub.data.framework.datasource.DataSource;
import tiendaclub.data.framework.index.maps.IndexSetMultimap;
import tiendaclub.data.framework.index.model.SetMultiMapIndex;
import tiendaclub.model.models.abstracts.IPersistible;

public abstract class IndexPersistible<V extends IPersistible> extends SetMultiMapIndex<Integer, V> {

    public IndexPersistible(DataSource<V> dataSource) {
        this.index = new IndexSetMultimap<>();
        this.dataSource = dataSource;
    }
}
