package casteldao;

import casteldao.misc.Flogger;
import casteldao.misc.Globals;
import com.google.common.base.MoreObjects;
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
public class DataSession implements Globals {


    protected Connection conn;

    protected String jdbcDriver = "";
    protected String jdbcIP = "";
    protected String jdbcPort = "";
    protected String jdbcCatalog = "";

    protected String jdbcUser = "";
    protected String jdbcPassword = "";

    private boolean autoclose = true;

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


    /**
     * establece la conexion a la DB
     *
     * @return true si la conexion fue establecida correctamente
     */
    public boolean connect(String jdbcIP, String jdbcPort, String jdbcCatalog, String jdbcUser, String jdbcPassword) {
        boolean success = false;
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(jdbcDriver + jdbcIP + ":" + jdbcPort + "/" + jdbcCatalog, jdbcUser, jdbcPassword);
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

    public boolean connect(String catalog) {
        return connect(jdbcIP, jdbcPort, catalog, jdbcUser, jdbcPassword);
    }

    public boolean connect() {
        return connect(jdbcIP, jdbcPort, jdbcCatalog, jdbcUser, jdbcPassword);
    }

    public boolean connectTry() {
        return connect(jdbcIP, jdbcPort, "", jdbcUser, jdbcPassword);
    }

    public boolean isLinkValid() {
        boolean valid = false;
        System.out.println(toString());
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
                    if (Globals.SQL_CONN) {
                        System.out.println("Connection has been closed.");
                    }
                } else {
                    if (Globals.SQL_CONN) {
                        System.out.println("Connection was already closed.");
                    }
                }
            } else {
                if (Globals.SQL_CONN) {
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
            this.autoclose = autoclose;
            close();
        } else {
            connect(catalog);
            this.autoclose = autoclose;
        }
        if (Globals.SQL_CONN) {
            System.out.println("Autoclose? " + autoclose);
        }
    }

    public void setAutoclose(boolean autoclose) {
        setAutoclose(autoclose, jdbcCatalog);
    }


    /**
     * Devuelve Una lista con los nombres de las tablas en una DB
     *
     * @return
     */
    public ArrayList<String> listTables(String catalogName) {
        String sql = "SHOW TABLES";
        ArrayList<String> tableNames = new ArrayList<>();
        if (connect(catalogName)) {
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    tableNames.add(rs.getString(1));
                }
                if (Globals.SQL_DEBUG) {
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

    public int numOfTables(String catalogName) {
        return listTables(catalogName).size();
    }

    public int numOfTables() {
        return numOfTables(jdbcCatalog);
    }




    /**
     * Devuelve Una lista con los nombres de las tablas en una DB
     *
     * @return
     */
    public ArrayList<String> listCatalogs() {
        return listPrefixedCatalogs("");
    }

    /**
     * Devuelve Una lista con los nombres de las tablas en una DB
     *
     * @return
     */
    public ArrayList<String> listPrefixedCatalogs(String catalogPrefix) {
        String sql = "SHOW DATABASES like '" + catalogPrefix + "%'";
        ArrayList<String> dbNames = new ArrayList<>();
        if (connectTry()) {
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    dbNames.add(rs.getString(1));
                }
                if (Globals.SQL_DEBUG) {
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


    /**
     * devuelve true si la estructura de la DB activa es valida (coincide con la inicializada)
     *
     * @return true si es valida
     */
    public boolean isCatalogValid(String catalogName, String catalogModel) {
        ArrayList<String> tables = listTables(catalogName);
        StringBuilder tablesString = new StringBuilder();
        tables.forEach(cnsmr -> tablesString.append(cnsmr).append("\n"));
        if (Globals.SQL_DEBUG)
            System.out.println(tablesString.toString());
        Flogger.atInfo().log("Valid Schema: " + catalogName + "->" + catalogModel.matches(tablesString.toString()));
        return catalogModel.matches(tablesString.toString());
    }

    public boolean isCatalogValid(String catalogModel) {
        return isCatalogValid(getJdbcCatalog(), catalogModel);
    }

    public ArrayList<String> listValidCatalogs(String catalogModel) {
        setAutoclose(false);
        ArrayList<String> list = listCatalogs();
        ArrayList<String> validList = new ArrayList<>();
        for (String catalogName : list) {
            if (isCatalogValid(catalogName, catalogModel)) {
                validList.add(catalogName);
            }
        }
        setAutoclose(true);
        return validList;
    }

    public boolean createCatalog(String catalogName) {
        boolean success = false;
        if (connectTry() && catalogName.length() > 1) {
            String sql = "CREATE DATABASE " + catalogName;
            try (Statement statement = conn.createStatement()) {
                success = statement.executeUpdate(sql) > 0;
                if (Globals.SQL_DEBUG) {
                    System.out.println(sql);
                }
            } catch (SQLException e) {
                Flogger.atSevere().withCause(e).log(sql);
            } finally {
                close();
            }
        }
        return success;
    }

    public boolean dropCatalog(String catalogName) {
        boolean success = false;
        if (connectTry() && catalogName.trim().length() > 1) {
            String sql = "DROP DATABASE " + catalogName;
            try (Statement statement = conn.createStatement()) {
                success = statement.executeUpdate(sql) > 0;
                if (Globals.SQL_DEBUG) {
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

    public boolean hasCatalog(String catalogName) {
        boolean hasCatalog = false;
        if (connectTry() && catalogName.trim().length() > 1) {
            String sql = "SHOW DATABASES like '" + catalogName.trim() + "'";
            try (Statement statement = conn.createStatement()) {
                hasCatalog = statement.execute(sql);
                if (Globals.SQL_DEBUG) {
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

    public boolean useCatalog(String catalogName) {
        boolean hasCatalog = false;
        if (connectTry() && catalogName.trim().length() > 1) {
            String sql = "USE '" + catalogName.trim() + "'";
            try (Statement statement = conn.createStatement()) {
                hasCatalog = statement.execute(sql);
                if (Globals.SQL_DEBUG) {
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

    public int createTables(String filepath) {
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
                    try (Statement stmt = getConn().createStatement()) {
                        stmt.executeUpdate(sql.trim());
                        rows++;
                        if (Globals.SQL_DEBUG) {
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
            if (Globals.SQL_DEBUG) {
                System.out.println("Tables created: " + rows);
            }
            close();
        }
        return rows;
    }

    public int createViews(String filepath) {
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
                    try (Statement stmt = getConn().createStatement()) {
                        stmt.executeUpdate(sql.trim());
                        rows++;
                        if (Globals.SQL_DEBUG) {
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
            if (Globals.SQL_DEBUG) {
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
                        if (Globals.SQL_DEBUG) {
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
            if (Globals.SQL_DEBUG) {
                System.out.println("Inserts: " + rows);
            }
            close();
        }
        return rows;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("jdbcDriver", jdbcDriver)
                          .add("jdbcIP", jdbcIP)
                          .add("jdbcPort", jdbcPort)
                          .add("jdbcCatalog", jdbcCatalog)
                          .add("jdbcUser", jdbcUser)
                          .add("jdbcPassword", jdbcPassword)
                          .add("autoclose", autoclose)
                          .toString();
    }
}
