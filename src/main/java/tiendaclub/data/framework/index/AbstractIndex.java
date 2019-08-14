package tiendaclub.data.framework.index;

import tiendaclub.data.framework.datasource.DataSource;
import tiendaclub.model.models.abstracts.Persistible;

import java.util.HashMap;

public abstract class AbstractIndex<K, T extends Persistible> {

    protected DataSource<T> dataSource;

    protected HashMap<K, ?> index;

    protected String INDEX_COL_NAME;

    public abstract void index(T objectT);

    public abstract void deindex(int id);

    public abstract void deindex(T objectT);

    public abstract void reindex(T objectT);

//    public abstract HashMap getIndexMap(K key);
//
//    public abstract HashMap getIndexMap();
//
//    public abstract Collection<T> getIndexValues(K key);
//
//    public abstract Collection<T> getIndexValues();
}
