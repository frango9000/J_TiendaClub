package tiendaclub.model.models.abstracts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IPersistible {

    int getId();

    void setId(int id);

    int insertIntoDB();

    int updateOnDb();

    int refreshFromDb();

    int deleteFromDb();

    void buildStatement(PreparedStatement pst) throws SQLException;

    void updateObject(ResultSet rs) throws SQLException;

    String getInsertString();

    String getUpdateString();

//
//    <T extends PersistibleDao<Persistible>> T getDataStore();

    String getTableName();

    ArrayList<String> getColNames();
}
