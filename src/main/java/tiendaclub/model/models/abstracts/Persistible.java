package tiendaclub.model.models.abstracts;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Persistible implements IPersistible {

    protected static String TABLE_NAME = "";
    protected static String ID_COL_NAME = "id";
    protected static ArrayList<String> COL_NAMES = new ArrayList<>();

    protected int id;

    protected Persistible(int id) {
        this.id = id;
    }

    public static String buildInsertString(String TABLE_NAME, ArrayList<String> COL_NAMES) {
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

    public static String buildUpdateString(String TABLE_NAME, String ID_COL_NAME, ArrayList<String> COL_NAMES, int id) {
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

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

}
