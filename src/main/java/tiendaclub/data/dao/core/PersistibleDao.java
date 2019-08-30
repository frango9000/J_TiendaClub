package tiendaclub.data.dao.core;

import java.util.ArrayList;
import tiendaclub.data.framework.DataSource;
import tiendaclub.data.framework.index.core.IIndex;
import tiendaclub.data.framework.model.IPersistible;
import tiendaclub.misc.Globals;

public class PersistibleDao<V extends IPersistible> implements Globals {

    protected ArrayList<IIndex<?, V>> indexes = new ArrayList<>();

    DataSource<V> dataSource;

    public PersistibleDao(String tableName) {
        this.dataSource = new DataSource<V>(tableName, getIndexes());
    }

    public DataSource<V> getDataSource() {
        return dataSource;
    }

    public ArrayList<IIndex<?, V>> getIndexes() {
        return indexes;
    }
}
