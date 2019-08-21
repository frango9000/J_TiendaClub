package tiendaclub.data.framework.dao.core;

import java.util.ArrayList;
import tiendaclub.data.framework.datasource.DataSource;
import tiendaclub.data.framework.index.core.AbstractIndex;
import tiendaclub.misc.Globals;
import tiendaclub.model.models.core.IPersistible;

public class PersistibleDao<V extends IPersistible> implements Globals {

    protected ArrayList<AbstractIndex<?, V>> indexes = new ArrayList<>();

    DataSource<V> dataSource;

    public PersistibleDao(String tableName) {
        this.dataSource = new DataSource<V>(tableName, getIndexes());
    }

    public DataSource<V> getDataSource() {
        return dataSource;
    }

    public ArrayList<AbstractIndex<?, V>> getIndexes() {
        return indexes;
    }
}
