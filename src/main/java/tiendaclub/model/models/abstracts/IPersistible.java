package tiendaclub.model.models.abstracts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface IPersistible {

    int getId();

    void setId(int id);

    int updateOnDb();

    int refreshFromDb();

    void buildStatement(PreparedStatement pst) throws SQLException;

    void updateObject(ResultSet rs) throws SQLException;

    String insertString();

    String updateString();
}
