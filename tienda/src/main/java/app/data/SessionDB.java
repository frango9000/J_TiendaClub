package app.data;

import app.misc.Flogger;
import app.misc.Globals;
import casteldao.DataSession;
import java.sql.SQLException;
import java.sql.Statement;

public class SessionDB extends DataSession {

    //Syncronized Singleton
    private static SessionDB instance;

    private SessionDB() {
        setJdbcDriver("jdbc:mysql://");
    }

    public static SessionDB getSession() {
        if (instance == null) {
            synchronized (SessionDB.class) {
                if (instance == null) {
                    instance = new SessionDB();
                }
            }
        }
        return instance;
    }


    public boolean isRoot() {
        boolean isRoot = false;
        String sql = "SHOW DATABASES like 'admtdc_%'";
        if (connectTry()) {
            try (Statement stmt = conn.createStatement()) {
                isRoot = stmt.execute(sql);
                if (Globals.SQL_DEBUG) {
                    System.out.println(sql);
                }
            } catch (SQLException ex) {
                Flogger.atSevere().withCause(ex).log();
            } finally {
                close();
            }
        }
        return isRoot;
    }

    public int createViews() {
        return createViews(SessionDB.class.getResource("/sql/views.sql").getPath());
    }

    public int createTables() {
        return createTables(SessionDB.class.getResource("/sql/tables.sql").getPath());
    }

    public int insertData() {
        return insertData(SessionDB.class.getResource("/sql/data.sql").getPath());
    }

    public int insertDemoData() {
        return insertData(SessionDB.class.getResource("/sql/demodata.sql").getPath());
    }

    public int createFullStructure() {
        int rows = 0;
        rows += createTables();
        rows += createViews();
        insertData();
        return rows;
    }
}
