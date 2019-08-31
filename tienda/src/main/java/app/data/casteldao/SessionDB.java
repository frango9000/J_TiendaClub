package app.data.casteldao;

import app.misc.Flogger;
import app.misc.Globals;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase Session se encagra de mantener la informacion de una conexion a una DB especifica.
 * Para multiples bases de datos crearemos multiples objetos SessionDB
 *
 * @author fsancheztemprano
 */
public final class SessionDB implements Globals {

    private static SessionDB instance;

    private SessionDB() {
    }

    public static SessionDB getSessionDB() {
        if (instance == null) {
            synchronized (SessionDB.class) {
                if (instance == null) {
                    instance = new SessionDB();
                }
            }
        }
        return instance;
    }

    private Connection conn;

    private String jdbcDriver = "jdbc:mysql://";
    private String jdbcIP = "";
    private String jdbcPort = "";
    private String jdbcCatalog = "";

    private String jdbcUser = "";
    private String jdbcPassword = "";

    private static boolean autoclose = true;


    /**
     * Getter para la clase Conexion
     *
     * @return Conexion
     */
    public Connection getConn() {
        return conn;
    }

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public String getJdbcIP() {
        return jdbcIP;
    }

    public void setJdbcIP(String jdbcIP) {
        this.jdbcIP = jdbcIP;
    }

    public String getJdbcPort() {
        return jdbcPort;
    }

    public void setJdbcPort(String jdbcPort) {
        this.jdbcPort = jdbcPort;
    }

    public String getJdbcCatalog() {
        return jdbcCatalog;
    }

    public void setJdbcCatalog(String jdbcCatalog) {
        this.jdbcCatalog = jdbcCatalog;
    }

    public String getJdbcUser() {
        return jdbcUser;
    }

    public void setJdbcUser(String jdbcUser) {
        this.jdbcUser = jdbcUser;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }

    public void setValues(String jdbcIP, String jdbcPort, String jdbcCatalog, String jdbcUser,
                          String jdbcPassword) {
        setJdbcIP(jdbcIP);
        setJdbcPort(jdbcPort);
        setJdbcCatalog(jdbcCatalog);
        setJdbcUser(jdbcUser);
        setJdbcPassword(jdbcPassword);
    }


    /**
     * establece la conexion a la DB
     *
     * @return true si la conexion fue establecida correctamente
     */
    public boolean connect(String jdbcIP, String jdbcPort, String jdbcCatalog, String jdbcUser,
                           String jdbcPassword) {
        boolean success = false;
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(
                    jdbcDriver + jdbcIP + ":" + jdbcPort + "/" + jdbcCatalog, jdbcUser, jdbcPassword);
                if (SQL_CONN) {
                    System.out.println(
                        "Connection to " + conn.getMetaData().getDriverName() + " has been established. Catalog: "
                        + conn.getCatalog());
                }
            } else {
                if (SQL_CONN) {
                    System.out.println("Connection to " + conn.getMetaData().getDriverName() + " already active.");
                }
            }
            success = true;
        } catch (SQLException e) {
            Flogger.atSevere().withCause(e).log();
        }
        return success;
    }

    public boolean connect(String catalog) {
        return connect(jdbcIP, jdbcPort, catalog, jdbcUser, jdbcPassword);
    }

    public boolean connect() {
        return connect(jdbcIP, jdbcPort, jdbcCatalog, jdbcUser, jdbcPassword);
    }

    public boolean connectTry() {
        return connect(jdbcIP, jdbcPort, "", jdbcUser, jdbcPassword);
    }

    public boolean isConnValid() {
        boolean valid = false;
        if (connectTry()) {
            try {
                valid = conn.isValid(30);
            } catch (SQLException ex) {
                Flogger.atSevere().withCause(ex).log();
            } finally {
                close();
            }
        }
        return valid;
    }

    public boolean isSessionValid() {
        boolean valid = false;
        if (connect()) {
            try {
                valid = conn.isValid(30);
            } catch (SQLException ex) {
                Flogger.atSevere().withCause(ex).log();
            } finally {
                close();
            }
        }
        return valid;
    }

    /**
     * Finaliza una conexion a la DB
     */
    public void close() {
        try {
            if (autoclose) {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                    if (SQL_CONN) {
                        System.out.println("Connection has been closed.");
                    }
                } else {
                    if (SQL_CONN) {
                        System.out.println("Connection was already closed.");
                    }
                }
            } else {
                if (SQL_CONN) {
                    System.out.println("Keeping conection alive.");
                }
            }
        } catch (SQLException e) {
            Flogger.atSevere().withCause(e).log();
        }
    }

    /**
     * @param autoclose
     */
    public void setAutoclose(boolean autoclose, String catalog) {
        if (autoclose) {
            SessionDB.autoclose = autoclose;
            close();
        } else {
            connect(catalog);
            SessionDB.autoclose = autoclose;
        }
        if (SQL_CONN) {
            System.out.println("Autoclose? " + autoclose);
        }
    }

    public void setAutoclose(boolean autoclose) {
        setAutoclose(autoclose, jdbcCatalog);
    }

    public boolean isRoot() {
        boolean isRoot = false;
        String sql = "SHOW DATABASES like 'admtdc_%'";
        if (connectTry()) {
            try (Statement stmt = conn.createStatement()) {
                isRoot = stmt.execute(sql);
                if (SQL_DEBUG) {
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

    /**
     * Devuelve Una lista con los nombres de las tablas en una DB
     *
     * @return
     */
    public ArrayList<String> listTables(String catalog) {
        String sql = "SHOW TABLES";
        ArrayList<String> tableNames = new ArrayList<>();
        if (connect(catalog)) {
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    tableNames.add(rs.getString(1));
                }
                if (SQL_DEBUG) {
                    System.out.println(sql);
                }
            } catch (SQLException ex) {
                Flogger.atSevere().withCause(ex).log();
            } finally {
                close();
            }
        }
        return tableNames;
    }

    public ArrayList<String> listTables() {
        return listTables(jdbcCatalog);
    }

    public int numOfTables(String catalog) {
        return listTables(catalog).size();
    }

    public int numOfTables() {
        return numOfTables(jdbcCatalog);
    }

    public void printTables(String catalog) {
        ArrayList<String> tablenames = listTables(catalog);
        tablenames.forEach((name) -> System.out.println(name));
    }

    public void printTables() {
        printTables(jdbcCatalog);
    }


    /**
     * Devuelve Una lista con los nombres de las tablas en una DB
     *
     * @return
     */
    public ArrayList<String> listCatalogs() {
        String sql = "SHOW DATABASES like '" + DB_PREFIX + "%'";
        ArrayList<String> dbNames = new ArrayList<>();
        if (connectTry()) {
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    dbNames.add(rs.getString(1));
                }
                if (SQL_DEBUG) {
                    System.out.println(sql);
                }
            } catch (SQLException ex) {
                Flogger.atSevere().withCause(ex).log();
            } finally {
                close();
            }
        }
        return dbNames;
    }

    public int numOfCatalogs() {
        return listCatalogs().size();
    }

    public void printCatalogs() {
        ArrayList<String> catalogs = listCatalogs();
        catalogs.forEach((name) -> System.out.println(name));
    }

    public ArrayList<String> listValidCatalogs() {
        setAutoclose(false);
        ArrayList<String> list = listCatalogs();
        ArrayList<String> validList = new ArrayList<>();
        for (String catalog : list) {
            if (isCatalogValid(catalog)) {
                validList.add(catalog);
            }
        }
        setAutoclose(true);
        return validList;
    }

    /**
     * devuelve true si la estructura de la DB activa es valida (coincide con la inicializada)
     *
     * @return true si es valida
     */
    public boolean isCatalogValid(String catalog) {
        ArrayList<String> tables = listTables(catalog);
        StringBuilder tablesString = new StringBuilder();
        tables.forEach(cnsmr -> tablesString.append(cnsmr).append("\n"));
        String model =
            "accesos\n" + "cajas\n" + "categorias\n" + "comprados\n" + "compras\n" + "monedero\n" + "productos\n"
            + "proveedores\n" + "sedes\n" + "socios\n" + "stock\n" + "transferencias\n" + "usuarios\n"
            + "vendidos\n" + "ventas\n" + "zs\n";
        if (SQL_DEBUG)
            System.out.println(tablesString.toString());
        Flogger.atInfo().log("Valid Schema: " + catalog + "->" + model.matches(tablesString.toString()));
        return model.matches(tablesString.toString());
    }

    public boolean isCatalogValid() {
        return isCatalogValid(getJdbcCatalog());
    }

    public boolean createCatalog(String dbname) {
        boolean success = false;
        if (connectTry() && dbname.length() > 1) {
            String sql = "CREATE DATABASE " + DB_PREFIX + dbname;
            try (Statement statement = conn.createStatement()) {
                success = statement.executeUpdate(sql) > 0;
                if (SQL_DEBUG) {
                    System.out.println(sql);
                }
            } catch (SQLException e) {
                Flogger.atSevere().withCause(e).log();
            } finally {
                close();
            }
        }
        return success;
    }

    public boolean dropCatalog(String dbname) {
        boolean success = false;
        if (connectTry() && dbname.length() > 1) {
            String sql = "DROP DATABASE " + dbname;
            try (Statement statement = conn.createStatement()) {
                success = statement.executeUpdate(sql) > 0;
                if (SQL_DEBUG) {
                    System.out.println(sql);
                }
            } catch (SQLException e) {
                Flogger.atSevere().withCause(e).log();
            } finally {
                close();
            }
        }
        return success;
    }

    public boolean hasCatalog(String dbname) {
        boolean hasCatalog = false;
        if (connectTry() && dbname.length() > 1) {
            String sql = "SHOW DATABASES like '" + DB_PREFIX + dbname.trim() + "'";
            try (Statement statement = conn.createStatement()) {
                hasCatalog = statement.execute(sql);
                if (SQL_DEBUG) {
                    System.out.println(sql);
                }
            } catch (SQLException e) {
                Flogger.atSevere().withCause(e).log();
            } finally {
                close();
            }
        }
        return hasCatalog;
    }

    //Catalog Creation Code

    public int createTables() {
        int rows = 0;
        File sqlFile = new File(SessionDB.class.getResource("/sql/tables.sql").getPath());
        StringBuilder sqlcmd = new StringBuilder();
        try (Scanner scan = new Scanner(new BufferedInputStream(new FileInputStream(sqlFile)))) {
            while (scan.hasNext()) {
                sqlcmd.append(scan.nextLine()).append("\n");
            }
            String multicmd = sqlcmd.toString();
            String[] cmds = multicmd.split(";");
            if (connect()) {
                for (String sql : cmds) {
                    try (Statement stmt = getConn().createStatement()) {
                        stmt.executeUpdate(sql.trim());
                        rows++;
                        if (SQL_DEBUG) {
                            System.out.println(sql);
                        }
                    } catch (SQLException ex) {
                        Flogger.atSevere().withCause(ex).log(sql);
                    }
                }
            }
        } catch (FileNotFoundException ignored) {
        } finally {
            if (SQL_DEBUG) {
                System.out.println("Tables created: " + rows);
            }
            close();
        }
        return rows;
    }

    public int createViews() {
        int rows = 0;
        File sqlFile = new File(SessionDB.class.getResource("/sql/views.sql").getPath());
        StringBuilder sqlcmd = new StringBuilder();
        try (Scanner scan = new Scanner(new BufferedInputStream(new FileInputStream(sqlFile)))) {
            while (scan.hasNext()) {
                sqlcmd.append(scan.nextLine()).append("\n");
            }
            String multicmd = sqlcmd.toString();
            String[] cmds = multicmd.split(";");
            if (connect()) {
                for (String sql : cmds) {
                    try (Statement stmt = getConn().createStatement()) {
                        stmt.executeUpdate(sql.trim());
                        rows++;
                        if (SQL_DEBUG) {
                            System.out.println(sql);
                        }
                    } catch (SQLException ex) {
                        Flogger.atSevere().withCause(ex).log(sql);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            Flogger.atSevere().withCause(e).log();
        } finally {
            if (SQL_DEBUG) {
                System.out.println("Views created: " + rows);
            }
            close();
        }
        return rows;
    }

    public int insertData(String filepath) {
        int rows = 0;
        File sqlFile = new File(filepath);
        StringBuilder sqlcmd = new StringBuilder();
        try (Scanner scan = new Scanner(new BufferedInputStream(new FileInputStream(sqlFile)))) {
            while (scan.hasNext()) {
                sqlcmd.append(scan.nextLine()).append("\n");
            }
            String multicmd = sqlcmd.toString();
            String[] cmds = multicmd.split(";");
            if (connect()) {
                for (String sql : cmds) {
                    try (Statement stmt = conn.createStatement()) {
                        rows += stmt.executeUpdate(sql.trim());
                        if (SQL_DEBUG) {
                            System.out.println(sql);
                        }
                    } catch (SQLException ex) {
                        Flogger.atSevere().withCause(ex).log(sql);
                    }
                }
            }
        } catch (FileNotFoundException ignored) {
        } finally {
            if (SQL_DEBUG) {
                System.out.println("Inserts: " + rows);
            }
            close();
        }
        return rows;
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
