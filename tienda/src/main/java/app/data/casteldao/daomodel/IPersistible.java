package app.data.casteldao.daomodel;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface IPersistible {

    int getId();

    void setId(int id);

    int insertIntoDB();

    int updateOnDb();

    int refreshFromDb();

    int deleteFromDb();

    void buildStatement(@NonNull PreparedStatement pst) throws SQLException;

    <T extends IPersistible> PersistibleDao<T> getDataStore();

    ArrayList<String> getColumnNames();

    String toStringFormatted();

    void setBackup() throws CloneNotSupportedException;

    Object getBackup();

    void restoreFromBackup();

    void commit();

    <V extends IPersistible> boolean restoreFrom(@NonNull V objectV);

}
