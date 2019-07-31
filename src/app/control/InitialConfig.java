package app.control;

import app.model.SessionDB;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InitialConfig {
    public static void setProps(File file) {
        System.out.println(file.getAbsolutePath());
        System.out.println(file.exists());
        try (FileInputStream f = new FileInputStream(file)) {
            Properties props = new Properties();
            props.load(f);

            SessionDB.setJdbcIP(props.getProperty("ip"));
            SessionDB.setJdbcPort(props.getProperty("port"));
            SessionDB.setJdbcDb(props.getProperty("dbname"));

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
        setProps("src/app/config.ini");
    }
}
