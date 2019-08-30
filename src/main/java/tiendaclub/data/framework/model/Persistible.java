package tiendaclub.data.framework.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import org.checkerframework.checker.nullness.qual.NonNull;
import tiendaclub.data.DataStore;
import tiendaclub.data.dao.core.PersistibleDao;

public abstract class Persistible extends Identifiable implements IPersistible, Cloneable, Serializable {


    protected String tableName;
    protected ArrayList<String> columnNames;


    protected Object backup;

    protected Persistible(int id) {
        super(id);
    }


    @Override
    public abstract <V extends IPersistible> boolean restoreFrom(@NonNull V objectV);

    @Override
    public void setBackup() throws CloneNotSupportedException {
        this.backup = this.clone();
    }

    @Override
    public Object getBackup() {
        return backup;
    }

    @Override
    public void restoreFromBackup() {
        if (backup != null) {
            restoreFrom((Persistible) backup);
        }
    }

    @Override
    public void commit() {
        this.backup = null;
    }


    @Override
    public int insertIntoDB() {
        if (getId() == 0) {
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
    public boolean refreshFromDb() {
        return getDataStore().getDataSource().updateObject(this);
    }

    @Override
    public int deleteFromDb() {
        return getDataStore().getDataSource().delete(this);
    }

    @Override
    public String getInsertString() {
        final StringBuilder sql = new StringBuilder(String.format("INSERT INTO %s VALUES(NULL, ", getTableName()));
        int i = getColumnNames().size();
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
        Iterator<String> iterator = getColumnNames().iterator();
        while (iterator.hasNext()) {
            sql.append(iterator.next()).append(" = ? ");
            if (iterator.hasNext()) {
                sql.append(", ");
            }
        }
        sql.append(String.format("WHERE %s = '%d'", getIdColName(), getId()));
        return sql.toString();
    }

    @SuppressWarnings("unchecked") // TODO verify cast
    @Override
    public <V extends IPersistible> PersistibleDao<V> getDataStore() {
        return DataStore.getDataStore(this);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public ArrayList<String> getColumnNames() {
        return columnNames;
    }

    @Override
    public String toStringFormatted() {
        return Integer.toString(getId());
    }
}
