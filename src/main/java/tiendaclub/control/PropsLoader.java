package tiendaclub.control;

import tiendaclub.data.SessionDB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropsLoader {

    public static final String defaultDir = "src/main/java/tiendaclub/";
    public static final String defaultFile = "config.ini";
    public static final String ini = defaultDir + defaultFile;

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

            SessionDB.setJdbcIP(props.getProperty("ip"));
            SessionDB.setJdbcPort(props.getProperty("port"));
            SessionDB.setJdbcCatalog(props.getProperty("dbname"));

            SessionDB.setJdbcUser(props.getProperty("user"));
            SessionDB.setJdbcPassword(props.getProperty("password"));
            SessionDB.setDbUrl();

            quickstart = Boolean.parseBoolean(props.getProperty("quickstart", "false"));

            return true;
        } catch (IOException ex) {
            Logger.getLogger(SessionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean loadProps(String filedir) {
        return loadProps(new File(filedir));
    }

    public static boolean loadProps() {
        return loadProps(ini);
    }

    public static boolean saveProps(File file) {
        Properties props = new Properties();
        props.put("ip", SessionDB.getJdbcIP());
        props.put("port", SessionDB.getJdbcPort());
        if (SessionDB.getJdbcCatalog().length() > 0)
            props.put("dbname", SessionDB.getJdbcCatalog());
        if (SessionDB.getJdbcUser().length() > 0)
            props.put("user", SessionDB.getJdbcUser());
        if (SessionDB.getJdbcPassword().length() > 0)
            props.put("password", SessionDB.getJdbcPassword());

        props.put("quickstart", quickstart ? "true" : "false");
        try (FileOutputStream f = new FileOutputStream(file)) {
            props.store(f, "ini");
            return true;
        } catch (IOException ex) {
            Logger.getLogger(PropsLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean saveProps() {
        return saveProps(new File(ini));
    }


}
