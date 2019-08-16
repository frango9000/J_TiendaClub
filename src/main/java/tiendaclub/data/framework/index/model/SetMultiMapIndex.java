package tiendaclub.data.framework.index.model;

import tiendaclub.data.framework.index.maps.IndexSetMultimap;

public abstract class SetMultiMapIndex<K, V> extends AbstractIndex<K, V> {

    {
        this.index = new IndexSetMultimap<>();
    }
}
