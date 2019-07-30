package app.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public interface IPersistible {

    String ID_COL_NAME = "id";

    int getId();

    void setId(int id);

    int updateOnDb();

    int refreshFromDb();

    void buildStatement(PreparedStatement pst) throws SQLException;

    void updateObject(ResultSet rs) throws SQLException;

    String insertString();

    String updateString();

    static String buildInsertString(String TABLE_NAME, ArrayList<String> COL_NAMES) {
        final StringBuilder sql = new StringBuilder(String.format("INSERT INTO %s VALUES(NULL, ", TABLE_NAME));
        int i = COL_NAMES.size();
        while (i > 0) {
            sql.append("? ");
            if (i > 1)
                sql.append(", ");
            i--;
        }
        return sql.append(")").toString();
    }

    static String buildUpdateString(String TABLE_NAME, String ID_COL_NAME, ArrayList<String> COL_NAMES, int id) {
        final StringBuilder sql = new StringBuilder(String.format("UPDATE %s SET ", TABLE_NAME));
        Iterator<String> iterator = COL_NAMES.iterator();
        while (iterator.hasNext()) {
            sql.append(iterator.next()).append(" = ? ");
            if (iterator.hasNext())
                sql.append(", ");
        }
        sql.append(String.format("WHERE %s = '%d'", ID_COL_NAME, id));
        return sql.toString();
    }
}
