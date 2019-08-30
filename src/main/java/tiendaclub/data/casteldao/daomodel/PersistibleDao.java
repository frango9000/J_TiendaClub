package tiendaclub.data.casteldao.daomodel;

import java.util.ArrayList;
import tiendaclub.data.casteldao.DataSource;
import tiendaclub.data.casteldao.index.core.IIndex;
import tiendaclub.misc.Globals;

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
