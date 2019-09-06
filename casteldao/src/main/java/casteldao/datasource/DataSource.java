package casteldao.datasource;

import casteldao.GenericDao;
import casteldao.Globals;
import casteldao.index.core.IIndex;
import casteldao.model.IEntity;
import java.io.Serializable;
import java.util.ArrayList;

public class DataSource<V extends IEntity<I>, I extends Serializable> implements Globals {

    protected ArrayList<IIndex<?, V, I>> indexes = new ArrayList<>();

    protected GenericDao<I, V> dao;

    public GenericDao<I, V> getDao() {
        return dao;
    }

    public ArrayList<IIndex<?, V, I>> getIndexes() {
        return indexes;
    }
}
