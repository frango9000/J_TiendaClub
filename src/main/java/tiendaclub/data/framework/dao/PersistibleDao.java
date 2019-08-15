package tiendaclub.data.framework.dao;

import java.util.ArrayList;
import tiendaclub.data.framework.datasource.DataSource;
import tiendaclub.data.framework.index.AbstractIndex;
import tiendaclub.model.Globals;
import tiendaclub.model.models.abstracts.Persistible;

public class PersistibleDao<T extends Persistible> implements Globals {

    protected ArrayList<AbstractIndex<?, T>> indexes = new ArrayList<>();

    DataSource<T> dataSource;

    public PersistibleDao(String TABLE_NAME) {
        this.dataSource = new DataSource<>(TABLE_NAME, getIndexes());
    }

    public DataSource<T> getDataSource() {
        return dataSource;
    }

    public ArrayList<AbstractIndex<?, T>> getIndexes() {
        return indexes;
    }
}
