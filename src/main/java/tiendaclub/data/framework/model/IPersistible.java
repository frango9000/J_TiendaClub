package tiendaclub.data.framework.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import org.checkerframework.checker.nullness.qual.NonNull;
import tiendaclub.data.dao.core.PersistibleDao;

public interface IPersistible {

    int getId();

    void setId(int id);

    int insertIntoDB();

    int updateOnDb();

    boolean refreshFromDb();

    int deleteFromDb();

    void buildStatement(@NonNull PreparedStatement pst) throws SQLException;

    <V extends IPersistible> boolean restoreFrom(@NonNull V objectV);

    String getInsertString();

    String getUpdateString();

    <T extends IPersistible> PersistibleDao<T> getDataStore();

    String getTableName();

    ArrayList<String> getColumnNames();

    String toStringFormatted();

    void setBackup() throws CloneNotSupportedException;

    Object getBackup();

    void restoreFromBackup();

    void commit();

}
