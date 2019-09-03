package casteldao.model;

import casteldao.dao.DataSource;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface IEntity<I extends Serializable> extends IIdentifiable<I> {

    String ID_COL_NAME = "id";

    void setId(ResultSet resultSet);

    boolean setEntity(ResultSet resultSet);

    boolean buildStatement(@NonNull PreparedStatement pst) throws SQLException;

    ArrayList<String> getColumnNames();

    <E extends IEntity<I>> DataSource<E, I> getDataStore();

    default int insertIntoDB() {
        return getDataStore().getDao().insert(this);
    }

    default int updateOnDb() {
        return getDataStore().getDao().update(this);
    }

    default int refreshFromDb() {
        return getDataStore().getDao().refresh(this);
    }

    default int deleteFromDb() {
        return getDataStore().getDao().delete(this);
    }

    IEntity<I> clone();

    IEntity getBackup();

    void setBackup() throws CloneNotSupportedException;

    static <T extends IEntity> void backupAll(Collection<T> backupees) {
        for (T t : backupees) {
            try {
                t.setBackup();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

    void restoreFromBackup();

    boolean restoreFrom(@NonNull IEntity objectV);

    void commit();


    default String toStringFormatted() {
        return getId().toString();
    }
}