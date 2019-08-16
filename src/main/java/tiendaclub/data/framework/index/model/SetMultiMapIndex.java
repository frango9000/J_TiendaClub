package tiendaclub.data.framework.index.model;

import tiendaclub.data.framework.index.maps.IndexSetMultimap;
import tiendaclub.model.models.abstracts.IPersistible;

public abstract class SetMultiMapIndex<K, V extends IPersistible> extends AbstractIndex<K, V> {

    {
        this.index = new IndexSetMultimap<>();
    }

}
