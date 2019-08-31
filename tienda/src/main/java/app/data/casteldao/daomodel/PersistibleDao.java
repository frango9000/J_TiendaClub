package app.data.casteldao.daomodel;

import app.data.casteldao.DataSource;
import app.data.casteldao.index.core.IIndex;
import app.misc.Globals;
import java.util.ArrayList;

public class PersistibleDao<V extends IPersistible> implements Globals {

    protected ArrayList<IIndex<?, V>> indexes = new ArrayList<>();

    DataSource<V> dataSource;

    public DataSource<V> getDataSource() {
        return dataSource;
    }

    public ArrayList<IIndex<?, V>> getIndexes() {
        return indexes;
    }
}
