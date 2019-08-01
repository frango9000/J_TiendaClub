package app.model;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase Session se encagra de mantener la informacion de una conexion a una DB
 * especifica, Para multiples mases de datos crearemos multiples objetos
 * SessionDB
 *
 * @author fsancheztemprano
 */
public final class SessionDB implements Globals {

    private static Connection conn;

    private static String jdbcString = "jdbc:mysql://";
    private static String jdbcIP = "";// = "localhost";
    private static String jdbcPort = "";// = "3306";
    private static String jdbcCatalog = "";// = "mv";

    private static String user = "";// = "narf";
    private static String password = "";// = "narff";


    private static String dbUrl = setDbUrl();

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

    public static String getJdbcString() {
        return jdbcString;
    }

    public static void setJdbcString(String jdbcString) {
        SessionDB.jdbcString = jdbcString;
    }

    public static String getJdbcIP() {
        return jdbcIP;
    }

    public static void setJdbcIP(String jdbcIP) {
        SessionDB.jdbcIP = jdbcIP;
        setDbUrl();
    }

    public static String getJdbcPort() {
        return jdbcPort;
    }

    public static void setJdbcPort(String jdbcPort) {
        SessionDB.jdbcPort = jdbcPort;
        setDbUrl();
    }

    public static String getJdbcCatalog() {
        return jdbcCatalog;
    }

    public static void setJdbcCatalog(String jdbcCatalog) {
        SessionDB.jdbcCatalog = jdbcCatalog;
        setDbUrl();
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        SessionDB.user = user;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        SessionDB.password = password;
    }


    public static String getDbUrl() {
        return dbUrl;
    }

    public static String setDbUrl() {
        return dbUrl = jdbcString + jdbcIP + ":" + jdbcPort + "/" + jdbcCatalog;
    }

    /**
     * establece la conexion a la DB
     *
     * @return true si la conexion fue establecida correctamente
     */
    public static boolean connect() {
        boolean success = false;
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(dbUrl, user, password);
                if (SQL_CONN) {
                    System.out.println("Connection to " + conn.getMetaData().getDriverName() + " has been established. DB: " + conn.getCatalog());
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

    /**
     * Finaliza una conexion a la DB
     */
    public static void close() {
        try {
            if (autoclose) {
                if (conn != null || !conn.isClosed()) {
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
                System.out.println("Keeping conection alive.");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * este metodo es para consultas en secuencia, al ejecutarlo
     * [setAutoclose(false)] se conecta a la DB y despues de todos los accesos a
     * la BD debe ser llamado nuevamente para asegurar la desconexion.
     * <p>
     * Ejemplo SessionDB.setAutoclose(false);
     * <p>
     * CategoriaDao.getInstance().queryAll();
     * ProductoDao.getInstance().queryAll(); categorizarProductos();
     * ArrayList<Orden> ordenesActivas =
     * MesaDao.getInstance().getOrdenesActivas();
     * ServidoDao.getInstance().queryByOrden(ordenesActivas);
     * MesaDao.getInstance().queryAll();
     * <p>
     * SessionDB.setAutoclose(true);
     *
     * @param autoclose
     */
    public static void setAutoclose(boolean autoclose) {
        if (autoclose) {
            SessionDB.autoclose = autoclose;
            close();
        } else {
            connect();
            SessionDB.autoclose = autoclose;
        }
        if (SQL_CONN) {
            System.out.println("Autoclose? " + autoclose);
        }
    }

    /**
     * Devuelve Una lista con los nombres de las tablas en una DB
     *
     * @return
     */
    public static ArrayList<String> listTables() {
        String sql = "SHOW TABLES";
        ArrayList<String> tableNames = new ArrayList<>();
        if (connect()) {
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    tableNames.add(rs.getString(1));
                }
                if (SQL_DEBUG) {
                    System.out.println(sql);
                    System.out.println("Catalog: " + conn.getCatalog());
                }
            } catch (SQLException ex) {
                Logger.getLogger(SessionDB.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                close();
            }
        }
        return tableNames;
    }

    public static int numOfTables() {
        return listTables().size();
    }

    public static void printTables() {
        ArrayList<String> tablenames = listTables();
        tablenames.forEach((name) -> System.out.println(name));
    }

    /**
     * Devuelve Una lista con los nombres de las tablas en una DB
     *
     * @return
     */
    public static ArrayList<String> listDBs() {
        String sql = "SHOW DATABASES like '" + Globals.DB_PREFIX + "%'";
        ArrayList<String> dbNames = new ArrayList<>();
        if (connect()) {
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

    public static int numOfDBs() {
        return listDBs().size();
    }

    public static void printDBs() {
        ArrayList<String> tablenames = listTables();
        tablenames.forEach((name) -> System.out.println(name));
    }


    public static int createTables() {
        int rows = 0;
        File sqlFile = new File("src/app/model/sql/tables.sql");
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
            close();
        }
        return rows;
    }

    public static int createViews() {
        int rows = 0;
        File sqlFile = new File("src/app/model/sql/views.sql");
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
            close();
        }
        return rows;
    }

    public static int createFullStructure() {
        int rows = 0;
        rows += createTables();
        rows += createViews();
        return rows;
    }

    public static int insertarDemoData() {
        int rows = 0;
        File sqlFile = new File("src/src/model/sql/demodata.sql");
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
                        stmt.executeUpdate(sql.trim());
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
            close();
        }
        return rows;
    }

    public static boolean isValid() {
        boolean valid = false;
        if (connect()) {
            try {
                valid = conn.isValid(10);
            } catch (SQLException ex) {
                Logger.getLogger(SessionDB.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                close();
            }
        }
        return valid;

    }


    /**
     * devuelve true si la estructura de la DB activa es valida (coincide con la
     * inicializada)
     *
     * @return true si es valida
     */
    public static boolean isSchemaValid() {
        ArrayList<String> tables = listTables();
        StringBuilder tablesString = new StringBuilder();
        tables.forEach(cnsmr -> tablesString.append(cnsmr).append("\n"));
        String model = "accesos\n" +
                "cajas\n" +
                "categorias\n" +
                "comprados\n" +
                "compras\n" +
                "monedero\n" +
                "productos\n" +
                "proveedores\n" +
                "sedes\n" +
                "socios\n" +
                "stock\n" +
                "transferencias\n" +
                "usuarios\n" +
                "vendidos\n" +
                "ventas\n" +
                "zs\n";
        //System.out.println(tablesString.toString());
        System.out.println("Valid Schema: " + model.matches(tablesString.toString()));
        return model.matches(tablesString.toString());
    }

    public static boolean createCatalog(String dbname) {
        boolean success = false;
        if (connect() && dbname.trim().length() > 1) {
            String sql = "CREATE DATABASE " + Globals.DB_PREFIX + dbname.trim();
            try (Statement statement = conn.createStatement()) {
                success = statement.execute(sql);
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
        if (connect() && dbname.trim().length() > 1) {
            String sql = "SHOW DATABASES like '" + Globals.DB_PREFIX + dbname.trim() + "'";
            try (Statement statement = conn.createStatement()) {
                ResultSet rs = statement.executeQuery(sql);
                if (rs.next())
                    hasCatalog = true;
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

    public static boolean isRoot() {
        boolean isRoot = false;
        String sql = "SHOW DATABASES like 'admtdc_%'";
        if (connect()) {
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next())
                    isRoot = true;
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
}
