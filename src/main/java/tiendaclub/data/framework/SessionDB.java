package tiendaclub.data.framework;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import tiendaclub.misc.Globals;

/**
 * Clase Session se encagra de mantener la informacion de una conexion a una DB especifica, Para multiples mases de
 * datos crearemos multiples objetos SessionDB
 *
 * @author fsancheztemprano
 */
public final class SessionDB implements Globals {

    private static Connection conn;

    private static String jdbcDriver = "jdbc:mysql://";
    private static String jdbcIP = "";
    private static String jdbcPort = "";
    private static String jdbcCatalog = "";

    private static String jdbcUser = "";
    private static String jdbcPassword = "";

    private static boolean autoclose = true;

    private SessionDB() {
    }

    /**
     * Getter para la clase Conexion
     *
     * @return Conexion
     */
    public static Connection getConn() {
        return conn;
    }

    public static String getJdbcDriver() {
        return jdbcDriver;
    }

    public static void setJdbcDriver(String jdbcDriver) {
        SessionDB.jdbcDriver = jdbcDriver;
    }

    public static String getJdbcIP() {
        return jdbcIP;
    }

    public static void setJdbcIP(String jdbcIP) {
        SessionDB.jdbcIP = jdbcIP;
    }

    public static String getJdbcPort() {
        return jdbcPort;
    }

    public static void setJdbcPort(String jdbcPort) {
        SessionDB.jdbcPort = jdbcPort;
    }

    public static String getJdbcCatalog() {
        return jdbcCatalog;
    }

    public static void setJdbcCatalog(String jdbcCatalog) {
        SessionDB.jdbcCatalog = jdbcCatalog;
    }

    public static String getJdbcUser() {
        return jdbcUser;
    }

    public static void setJdbcUser(String jdbcUser) {
        SessionDB.jdbcUser = jdbcUser;
    }

    public static String getJdbcPassword() {
        return jdbcPassword;
    }

    public static void setJdbcPassword(String jdbcPassword) {
        SessionDB.jdbcPassword = jdbcPassword;
    }

    public static void setValues(String jdbcIP, String jdbcPort, String jdbcCatalog, String jdbcUser, String jdbcPassword) {
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
    public static boolean connect(String jdbcIP, String jdbcPort, String jdbcCatalog, String jdbcUser, String jdbcPassword) {
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
            System.out.println(e.getMessage());
        }
        return success;
    }

    public static boolean connect(String catalog) {
        return connect(jdbcIP, jdbcPort, catalog, jdbcUser, jdbcPassword);
    }

    public static boolean connect() {
        return connect(jdbcIP, jdbcPort, jdbcCatalog, jdbcUser, jdbcPassword);
    }

    public static boolean connectTry() {
        return connect(jdbcIP, jdbcPort, "", jdbcUser, jdbcPassword);
    }

    public static boolean isConnValid() {
        boolean valid = false;
        if (connectTry()) {
            try {
                valid = conn.isValid(30);
            } catch (SQLException ex) {
                Logger.getLogger(SessionDB.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                close();
            }
        }
        return valid;
    }

    public static boolean isSessionValid() {
        boolean valid = false;
        if (connect()) {
            try {
                valid = conn.isValid(30);
            } catch (SQLException ex) {
                Logger.getLogger(SessionDB.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                close();
            }
        }
        return valid;
    }

    /**
     * Finaliza una conexion a la DB
     */
    public static void close() {
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
            e.printStackTrace();
        }
    }

    /**
     * @param autoclose
     */
    public static void setAutoclose(boolean autoclose, String catalog) {
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

    public static void setAutoclose(boolean autoclose) {
        setAutoclose(autoclose, jdbcCatalog);
    }

    public static boolean isRoot() {
        boolean isRoot = false;
        String sql = "SHOW DATABASES like 'admtdc_%'";
        if (connectTry()) {
            try (Statement stmt = conn.createStatement()) {
                isRoot = stmt.execute(sql);
                if (SQL_DEBUG) {
                    System.out.println(sql);
                }
            } catch (SQLException ex) {
                Logger.getLogger(SessionDB.class.getName()).log(Level.SEVERE, null, ex);
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
    public static ArrayList<String> listTables(String catalog) {
        String sql = "SHOW TABLES";
        ArrayList<String> tableNames = new ArrayList<>();
        System.out.println(catalog);
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
                Logger.getLogger(SessionDB.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                close();
            }
        }
        return tableNames;
    }

    public static ArrayList<String> listTables() {
        return listTables(jdbcCatalog);
    }

    public static int numOfTables(String catalog) {
        return listTables(catalog).size();
    }

    public static int numOfTables() {
        return numOfTables(jdbcCatalog);
    }

    public static void printTables(String catalog) {
        ArrayList<String> tablenames = listTables(catalog);
        tablenames.forEach((name) -> System.out.println(name));
    }

    public static void printTables() {
        printTables(jdbcCatalog);
    }


    /**
     * Devuelve Una lista con los nombres de las tablas en una DB
     *
     * @return
     */
    public static ArrayList<String> listCatalogs() {
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
                Logger.getLogger(SessionDB.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                close();
            }
        }
        return dbNames;
    }

    public static int numOfCatalogs() {
        return listCatalogs().size();
    }

    public static void printCatalogs() {
        ArrayList<String> catalogs = listCatalogs();
        catalogs.forEach((name) -> System.out.println(name));
    }

    public static ArrayList<String> listValidCatalogs() {
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
    public static boolean isCatalogValid(String catalog) {
        ArrayList<String> tables = listTables(catalog);
        StringBuilder tablesString = new StringBuilder();
        tables.forEach(cnsmr -> tablesString.append(cnsmr).append("\n"));
        String model =
                "accesos\n" + "cajas\n" + "categorias\n" + "comprados\n" + "compras\n" + "monedero\n" + "productos\n"
                        + "proveedores\n" + "sedes\n" + "socios\n" + "stock\n" + "transferencias\n" + "usuarios\n"
                        + "vendidos\n" + "ventas\n" + "zs\n";
        System.out.println(tablesString.toString());
        System.out.println("Valid Schema: " + catalog + "->" + model.matches(tablesString.toString()));
        return model.matches(tablesString.toString());
    }

    public static boolean isCatalogValid() {
        return isCatalogValid(getJdbcCatalog());
    }

    public static boolean createCatalog(String dbname) {
        boolean success = false;
        if (connectTry() && dbname.length() > 1) {
            String sql = "CREATE DATABASE " + DB_PREFIX + dbname;
            try (Statement statement = conn.createStatement()) {
                success = statement.executeUpdate(sql) > 0;
                if (SQL_DEBUG) {
                    System.out.println(sql);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }
        return success;
    }

    public static boolean dropCatalog(String dbname) {
        boolean success = false;
        if (connectTry() && dbname.length() > 1) {
            String sql = "DROP DATABASE " + dbname;
            try (Statement statement = conn.createStatement()) {
                success = statement.executeUpdate(sql) > 0;
                if (SQL_DEBUG) {
                    System.out.println(sql);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }
        return success;
    }

    public static boolean hasCatalog(String dbname) {
        boolean hasCatalog = false;
        if (connectTry() && dbname.length() > 1) {
            String sql = "SHOW DATABASES like '" + DB_PREFIX + dbname.trim() + "'";
            try (Statement statement = conn.createStatement()) {
                hasCatalog = statement.execute(sql);
                if (SQL_DEBUG) {
                    System.out.println(sql);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }
        return hasCatalog;
    }

    //Catalog Creation Code

    public static int createTables() {
        int rows = 0;
        File sqlFile = new File(Globals.ROOT_PATH + "model/sql/tables.sql");
        StringBuilder sqlcmd = new StringBuilder();
        try (Scanner scan = new Scanner(new BufferedInputStream(new FileInputStream(sqlFile)))) {
            while (scan.hasNext()) {
                sqlcmd.append(scan.nextLine()).append("\n");
            }
            String multicmd = sqlcmd.toString();
            String[] cmds = multicmd.split(";");
            if (connect()) {
                for (String sql : cmds) {
                    try (Statement stmt = SessionDB.getConn().createStatement()) {
                        stmt.executeUpdate(sql.trim());
                        rows++;
                        if (SQL_DEBUG) {
                            System.out.println(sql);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(SessionDB.class.getName()).log(Level.SEVERE, sql, ex);
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

    public static int createViews() {
        int rows = 0;
        File sqlFile = new File(Globals.ROOT_PATH + "model/sql/views.sql");
        StringBuilder sqlcmd = new StringBuilder();
        try (Scanner scan = new Scanner(new BufferedInputStream(new FileInputStream(sqlFile)))) {
            while (scan.hasNext()) {
                sqlcmd.append(scan.nextLine()).append("\n");
            }
            String multicmd = sqlcmd.toString();
            String[] cmds = multicmd.split(";");
            if (connect()) {
                for (String sql : cmds) {
                    try (Statement stmt = SessionDB.getConn().createStatement()) {
                        stmt.executeUpdate(sql.trim());
                        rows++;
                        if (SQL_DEBUG) {
                            System.out.println(sql);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(SessionDB.class.getName()).log(Level.SEVERE, sql, ex);
                    }
                }
            }
        } catch (FileNotFoundException ignored) {
        } finally {
            if (SQL_DEBUG) {
                System.out.println("Views created: " + rows);
            }
            close();
        }
        return rows;
    }

    public static int insertData(String filepath) {
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
                        Logger.getLogger(SessionDB.class.getName()).log(Level.SEVERE, sql, ex);
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

    public static int insertData() {
        return insertData(Globals.ROOT_PATH + "model/sql/data.sql");
    }

    public static int insertDemoData() {
        return insertData(Globals.ROOT_PATH + "model/sql/demodata.sql");
    }

    public static int createFullStructure() {
        int rows = 0;
        rows += createTables();
        rows += createViews();
        insertData();
        return rows;
    }
}
