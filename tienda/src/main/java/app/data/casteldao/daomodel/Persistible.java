package app.data.casteldao.daomodel;

import java.io.Serializable;
import java.util.Collection;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class Persistible extends Identifiable implements IPersistible, Cloneable, Serializable {

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
    public int refreshFromDb() {
        return getDataStore().getDataSource().refresh(this);
    }

    @Override
    public int deleteFromDb() {
        return getDataStore().getDataSource().delete(this);
    }

    @Override
    public abstract <V extends IPersistible> PersistibleDao<V> getDataStore();

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toStringFormatted() {
        return Integer.toString(getId());
    }

    public static <T extends Persistible> void backupList(Collection<T> backupees) {
        for (T t : backupees) {
            try {
                t.setBackup();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }
}
