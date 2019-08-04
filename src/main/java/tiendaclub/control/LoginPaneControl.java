package tiendaclub.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import tiendaclub.control.misc.StaticHelpers;
import tiendaclub.model.SessionDB;
import tiendaclub.view.FxDialogs;

public class LoginPaneControl {

    @FXML
    private MenuItem menuLoad;

    @FXML
    private MenuItem menuSave;

    @FXML
    private MenuItem menuClose;

    @FXML
    private MenuItem menuDbConfig;

    @FXML
    private MenuItem menuTest;

    @FXML
    private MenuItem menuAbout;

    @FXML
    private Button buttonEnter;

    @FXML
    private TextField textFieldUser;

    @FXML
    private PasswordField passwordField;

    @FXML
    void menuCloseAct(ActionEvent event) {

    }

    @FXML
    void menuDbConfigAct(ActionEvent event) {
        String ip = null;
        while (ip == null || ip.length() < 5) {
            ip = FxDialogs.showTextInput("Db IP", "Enter DB IP", SessionDB.getJdbcIP()).trim();
        }
        String port = null;
        while (true) {
            port = FxDialogs.showTextInput("DB Port", "Enter DB Port", SessionDB.getJdbcPort()).trim();
            if (port != null && port.length() > 0 && StaticHelpers.isInteger(port)) {
                int portnum = Integer.parseInt(port);
                if (portnum > 0 && portnum < 65536)
                    break;
            }
        }
        System.out.println(ip + ":" + port);
        SessionDB.setJdbcIP(ip);
        SessionDB.setJdbcPort(port);
    }

    @FXML
    void menuLoadAct(ActionEvent event) {

    }

    @FXML
    void menuSaveAct(ActionEvent event) {

    }

    @FXML
    void menuTestAct(ActionEvent event) {
//        SessionDB.setUser(jTextFieldUser.getText().trim());
//        SessionDB.setPassword(new String(jPasswordField1.getPassword()).trim());
//        if (SessionDB.isValid()) {
//            buttonEnter.setStyle("-fx-background-color: Yellow");
//            System.out.println(SessionDB.numOfDBs());
//            if (SessionDB.numOfDBs() == 0) {
//                if (!SessionDB.isRoot())
//                    JOptionPane.showMessageDialog(null, "No valid DBs and no access to create.", "DB Wizard", JOptionPane.PLAIN_MESSAGE, null);
//                else {
//                    if (createDB()) {
//                        JOptionPane.showMessageDialog(null, "DB creation Success", "DB Wizard", JOptionPane.PLAIN_MESSAGE, null);
//                        System.out.println(SessionDB.createFullStructure());
//                        if (SessionDB.isSchemaValid()) {
//                            jButtonTest.setBackground(Color.green);
//                            JOptionPane.showMessageDialog(null, "Structure creation Success", "DB Wizard", JOptionPane.PLAIN_MESSAGE, null);
//                        } else {
//                            jButtonTest.setBackground(Color.yellow);
//                            JOptionPane.showMessageDialog(null, "Structure creation Fail", "DB Wizard", JOptionPane.PLAIN_MESSAGE, null);
//                        }
//                    } else {
//                        JOptionPane.showMessageDialog(null, "DB Creation Fail", "DB Wizard", JOptionPane.PLAIN_MESSAGE, null);
//                        jButtonTest.setBackground(Color.red);
//                    }
//                }
//            } else {
//                SessionDB.setJdbcCatalog(pickDb());
//                if (SessionDB.isSchemaValid())
//                    buttonEnter.setStyle("-fx-background-color: green");
//            }
//        } else buttonEnter.setStyle("-fx-background-color: red");
    }

    public static String pickDb() {
//        String[] dbslist = SessionDB.listDBs().toArray(new String[0]);
//        return JOptionPane.showInputDialog(null, "Pick a DB", "Pick a DB", JOptionPane.PLAIN_MESSAGE, null, dbslist, null).toString();
        return "";
    }

    public static boolean createDB() {
//        ArrayList<String> list = SessionDB.listDBs();
//        JOptionPane.showMessageDialog(null, "Creating new DB", "DB Wizard", JOptionPane.PLAIN_MESSAGE, null);
//        String name = "";
//        do {
//            name = JOptionPane.showInputDialog(new JFrame(), "Enter DB name", "DB Name", JOptionPane.PLAIN_MESSAGE, null, null, "").toString().trim();
//        } while (name.length() < 1 || name == null || list.contains("tdc_" + name));
//
//        JOptionPane.showMessageDialog(null, "Creating DB name: " + name, "DB Wizard", JOptionPane.PLAIN_MESSAGE, null);
//        SessionDB.createCatalog(name);
//        if (SessionDB.hasCatalog(name)) {
//            SessionDB.setJdbcCatalog(Globals.DB_PREFIX + name);
//            return true;
//        } else return false;
        return true;
    }
}
