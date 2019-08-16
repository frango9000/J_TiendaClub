package tiendaclub.model.models.abstracts;

import java.util.ArrayList;
import java.util.Iterator;
import tiendaclub.data.DataStore;
import tiendaclub.data.framework.dao.PersistibleDao;

public abstract class Persistible extends Identifiable implements IPersistible {

    protected Persistible(int id) {
        super(id);
    }

    @Override
    public int insertIntoDB() {
        if (getId() == 0) {
//            return getDataStore().insert(this);
            return getDataStore().getDataSource().insert(this);
        } else {
            return 0;
        }
    }

    @Override
    public int updateOnDb() {
        return getDataStore().getDataSource().update(this);
    }

    @Override
    public int refreshFromDb() {
        return getDataStore().getDataSource().updateObject(this);
    }

    @Override
    public int deleteFromDb() {
        return getDataStore().getDataSource().delete(this);
    }

    @Override
    public String getInsertString() {
        final StringBuilder sql = new StringBuilder(String.format("INSERT INTO %s VALUES(NULL, ", getTableName()));
        int i = getColNames().size();
        while (i > 0) {
            sql.append("? ");
            if (i > 1) {
                sql.append(", ");
            }
            i--;
        }
        return sql.append(")").toString();
    }

    @Override
    public String getUpdateString() {
        final StringBuilder sql = new StringBuilder(String.format("UPDATE %s SET ", getTableName()));
        Iterator<String> iterator = getColNames().iterator();
        while (iterator.hasNext()) {
            sql.append(iterator.next()).append(" = ? ");
            if (iterator.hasNext()) {
                sql.append(", ");
            }
        }
        sql.append(String.format("WHERE %s = '%d'", getIdColName(), getId()));
        return sql.toString();
    }

    @Override
    public <T extends IPersistible> PersistibleDao<T> getDataStore() {
        return DataStore.getDataStore(this);
    }

    @Override
    public abstract String getTableName();

    @Override
    public abstract ArrayList<String> getColNames();
}
