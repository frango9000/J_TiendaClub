package app.misc;

import app.data.SessionDB;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

public class PropsLoader {

    public static final URL defaultFile = PropsLoader.class.getResource("/config.ini");

    private static boolean quickstart = false;

    public static boolean isQuickstart() {
        return quickstart;
    }

    public static boolean loadProps(File file) {
        System.out.println(file.getAbsolutePath());
        System.out.println(file.exists());
        try (FileInputStream f = new FileInputStream(file)) {
            Properties props = new Properties();
            props.load(f);
            SessionDB sessionDB = SessionDB.getSession();
            sessionDB.setJdbcIP(props.getProperty("ip"));
            sessionDB.setJdbcPort(props.getProperty("port"));
            sessionDB.setJdbcCatalog(props.getProperty("dbname"));

            sessionDB.setJdbcUser(props.getProperty("user"));
            sessionDB.setJdbcPassword(props.getProperty("password"));

            quickstart = Boolean.parseBoolean(props.getProperty("quickstart", "false"));

            return true;
        } catch (IOException e) {
            Flogger.atSevere().withCause(e).log();
        }
        return false;
    }

    public static boolean loadProps(String filedir) {
        return loadProps(new File(filedir));
    }

    public static boolean loadProps() {
        return getDefaultFile() != null && loadProps(new File(getDefaultFile()));
    }

    public static boolean saveProps(File file) {
        SessionDB sessionDB = SessionDB.getSession();
        Properties props = new Properties();
        props.put("ip", sessionDB.getJdbcIP());
        props.put("port", sessionDB.getJdbcPort());
        if (sessionDB.getJdbcCatalog().length() > 0) {
            props.put("dbname", sessionDB.getJdbcCatalog());
        }
        if (sessionDB.getJdbcUser().length() > 0) {
            props.put("user", sessionDB.getJdbcUser());
        }
        if (sessionDB.getJdbcPassword().length() > 0) {
            props.put("password", sessionDB.getJdbcPassword());
        }

        props.put("quickstart", quickstart ? "true" : "false");
        try (FileOutputStream f = new FileOutputStream(file)) {
            props.store(f, "ini");
            return true;
        } catch (IOException e) {
            Flogger.atSevere().withCause(e).log();
        }
        return false;
    }

    public static boolean saveProps(URI url) {
        return saveProps(new File(url));
    }

    public static boolean saveProps() {
        return saveProps(getDefaultFile());
    }

    public static URI getDefaultFile() {
        try {
            return defaultFile.toURI();
        } catch (URISyntaxException e) {
            Flogger.atSevere().withCause(e).log();
            return null;
        }
    }
}
