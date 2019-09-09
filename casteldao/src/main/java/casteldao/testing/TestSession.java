package casteldao.testing;

import casteldao.DataSession;
import casteldao.misc.Flogger;
import casteldao.misc.Globals;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestSession extends DataSession {

    //Syncronized Singleton
    private static TestSession instance;

    private TestSession() {
        setJdbcDriver("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
    }

    public static TestSession getSession() {
        if (instance == null) {
            synchronized (TestSession.class) {
                if (instance == null) {
                    instance = new TestSession();
                }
            }
        }
        return instance;
    }


    @Override
    public boolean connect(String jdbcIP, String jdbcPort, String jdbcCatalog, String jdbcUser, String jdbcPassword) {
        boolean success = false;
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(jdbcDriver);
                if (Globals.SQL_CONN) {
                    System.out.println("Connection to " + conn.getMetaData().getDriverName() + " has been established. Catalog: " + conn.getCatalog());
                }
            } else {
                if (Globals.SQL_CONN) {
                    System.out.println("Connection to " + conn.getMetaData().getDriverName() + " already active.");
                }
            }
            success = true;
        } catch (SQLException e) {
            Flogger.atSevere().withCause(e).log(toString());
        }
        return success;
    }

    public int createTables() {
        return createTables(TestSession.class.getResource("/test/tables.sql").getPath());
    }

    public int insertData() {
        return createTables(TestSession.class.getResource("/test/data.sql").getPath());
    }

    public int init() {
        return createTables() + insertData();
    }

}
