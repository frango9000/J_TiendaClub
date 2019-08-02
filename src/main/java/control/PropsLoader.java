package control;

import model.SessionDB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropsLoader {

    private static String ini = "src/main/java/config.ini";

    public static void setProps(File file) {
        System.out.println(file.getAbsolutePath());
        System.out.println(file.exists());
        try (FileInputStream f = new FileInputStream(file)) {
            Properties props = new Properties();
            props.load(f);

            SessionDB.setJdbcIP(props.getProperty("ip"));
            SessionDB.setJdbcPort(props.getProperty("port"));
            SessionDB.setJdbcCatalog(props.getProperty("dbname"));

            SessionDB.setUser(props.getProperty("user"));
            SessionDB.setPassword(props.getProperty("password"));
            SessionDB.setDbUrl();
        } catch (IOException ex) {
            Logger.getLogger(SessionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setProps(String filedir) {
        setProps(new File(filedir));
    }

    public static void setProps() {
        setProps(ini);
    }

    public static void saveProps(File file) {
        Properties props = new Properties();
        props.put("ip", SessionDB.getJdbcIP());
        props.put("port", SessionDB.getJdbcPort());
        props.put("dbname", SessionDB.getJdbcCatalog());
        props.put("user", SessionDB.getUser());
        props.put("password", SessionDB.getPassword());
        try (FileOutputStream f = new FileOutputStream(file)) {
            props.store(f, "ini");
        } catch (IOException ex) {
            Logger.getLogger(PropsLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void saveProps() {
        saveProps(new File(ini));
    }


}
