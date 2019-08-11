package tiendaclub.data.framework.index;

import java.util.Collection;
import java.util.HashMap;

public abstract class AbstractIndex<K, T> {

    protected String INDEX_COL_NAME = "";

    protected HashMap index;

    public abstract void index(T objectT);

    public abstract void deindex(int id);

    public abstract void deindex(T objectT);

    public abstract void reindex(T objectT);

    public abstract HashMap getIndexMap(K key);

    public abstract HashMap getIndexMap();

    public abstract Collection<T> getIndexList(K key);

    public abstract Collection<T> getIndexList();
}
