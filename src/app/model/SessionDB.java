package app.model;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
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
public final class SessionDB implements Globals{

    private static Connection conn;
    
    private static String jdbcString = "jdbc:mysql://";
    private static String jdbcIP = "localhost";
    private static String jdbcPort = "3306";
    private static String jdbcDbName = "mv";
    
    private static String user = "narf";
    private static String password = "narff";
    
    
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

    public static String getJdbcDb() {
        return jdbcDbName;
    }

    public static void setJdbcDb(String jdbcDb) {
        SessionDB.jdbcDbName = jdbcDb;
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
    
    public static String setDbUrl(){
        return dbUrl = jdbcString + jdbcIP+":"+jdbcPort+"/"+jdbcDbName;
    }

    public static void setProps(File file){
        System.out.println(file.getAbsolutePath());
        System.out.println(file.exists());
        try(FileInputStream f = new FileInputStream(file)){
            Properties props = new Properties();
            props.load(f);
            
            jdbcIP= props.getProperty("ip");
            jdbcPort=props.getProperty("port");
            jdbcDbName = props.getProperty("dbname");
            
            user = props.getProperty("user");
            password = props.getProperty("password");
            setDbUrl();
        } catch (IOException ex) {
            Logger.getLogger(SessionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void setProps(String filedir){
        setProps(new File(filedir));
    }
    public static void setProps(){
        setProps("src/app/config.ini");
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
                    System.out.println("Connection to " + conn.getMetaData().getDriverName() + " has been established.\n" + conn.getCatalog());
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
     *
     * Ejemplo SessionDB.setAutoclose(false);
     *
     * CategoriaDao.getInstance().queryAll();
     * ProductoDao.getInstance().queryAll(); categorizarProductos();
     * ArrayList<Orden> ordenesActivas =
     * MesaDao.getInstance().getOrdenesActivas();
     * ServidoDao.getInstance().queryByOrden(ordenesActivas);
     * MesaDao.getInstance().queryAll();
     *
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
        connect();
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
        String sql = "SHOW DATABASES";
        ArrayList<String> dbNames = new ArrayList<>();
        connect();
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
        return dbNames;
    }

    public static int numOfDBs() {
        return listDBs().size();
    }

    public static void printDBs() {
        ArrayList<String> tablenames = listTables();
        tablenames.forEach((name) -> System.out.println(name));
    }


    public static int crearTablas() {
        int rows = 0;
        File sqlFile = new File("src/app/model/tables.sql");
        StringBuilder sqlcmd = new StringBuilder();
        try (Scanner scan = new Scanner(new BufferedInputStream(new FileInputStream(sqlFile)))) {
            while (scan.hasNext()) {
                sqlcmd.append(scan.nextLine()).append("\n");
            }
            String multicmd = sqlcmd.toString();
            String[] cmds = multicmd.split(";");
            SessionDB.connect();
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
        } catch (FileNotFoundException ignored) {
        } finally {
            SessionDB.close();
        }
        return rows;
    }

    public static int insertarDemoData() {
        int rows = 0;
        File sqlFile = new File("src/src/model/DemoData.sql");
        StringBuilder sqlcmd = new StringBuilder();
        try (Scanner scan = new Scanner(new BufferedInputStream(new FileInputStream(sqlFile)))) {
            while (scan.hasNext()) {
                sqlcmd.append(scan.nextLine()).append("\n");
            }
            String multicmd = sqlcmd.toString();
            String[] cmds = multicmd.split(";");
            SessionDB.connect();
            for (String sql : cmds) {
                try (Statement stmt = SessionDB.getConn().createStatement()) {
                    stmt.executeUpdate(sql.trim());
                    if (SQL_DEBUG) {
                        System.out.println(sql);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(SessionDB.class.getName()).log(Level.SEVERE, sql, ex);
                }
            }
        } catch (FileNotFoundException ignored) {
        } finally {
            SessionDB.close();
        }
        return rows;
    }

    /**
     * devuelve true si la estructura de la DB activa es valida (coincide con la
     * inicializada)
     *
     * @return true si es valida
     */
    public static boolean isValid() {
        ArrayList<String> tables = listTables();
        StringBuilder tablesString = new StringBuilder();
        tables.forEach(cnsmr -> tablesString.append(cnsmr).append("\n"));
        String model = "accesos\n" +
                "cajas\n" +
                "categorias\n" +
                "comprados\n" +
                "compras\n" +
                "productos\n" +
                "proveedores\n" +
                "sedes\n" +
                "socios\n" +
                "stock\n" +
                "usuarios\n" +
                "vendidos\n" +
                "ventas\n" +
                "zs\n";
        //System.out.println(tablesString.toString());
        System.out.println("Valid DB: " + model.matches(tablesString.toString()));
        return model.matches(tablesString.toString());
    }
}
