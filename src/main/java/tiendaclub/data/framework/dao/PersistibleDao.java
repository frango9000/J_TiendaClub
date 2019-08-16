package tiendaclub.data.framework.dao;

import java.util.ArrayList;
import tiendaclub.data.framework.datasource.DataSource;
import tiendaclub.data.framework.index.model.AbstractIndex;
import tiendaclub.model.Globals;
import tiendaclub.model.models.abstracts.IPersistible;

public class PersistibleDao<V extends IPersistible> implements Globals {

    protected ArrayList<AbstractIndex<?, V>> indexes = new ArrayList<>();

    DataSource<V> dataSource;

    public PersistibleDao(String TABLE_NAME) {
        this.dataSource = new DataSource<>(TABLE_NAME, getIndexes());
    }

    public DataSource<V> getDataSource() {
        return dataSource;
    }

    public ArrayList<AbstractIndex<?, V>> getIndexes() {
        return indexes;
    }
}
