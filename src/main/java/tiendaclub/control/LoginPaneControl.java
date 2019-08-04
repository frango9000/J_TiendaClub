package tiendaclub.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import tiendaclub.MainFX;
import tiendaclub.control.misc.StaticHelpers;
import tiendaclub.model.DataStore;
import tiendaclub.model.Globals;
import tiendaclub.model.SessionDB;
import tiendaclub.model.models.Usuario;
import tiendaclub.view.FxDialogs;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginPaneControl implements Initializable {

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

    public static boolean createDB() {
        ArrayList<String> list = SessionDB.listCatalogs();
        FxDialogs.showInfo("DB Wizard", "Creating new DB");
        String name = "";
        do {
            name = FxDialogs.showTextInput("DB Name", "Enter DB name");
        } while (name.length() < 1 || name == null || list.contains("tdc_" + name));

        FxDialogs.showInfo("DB Wizard", "Creating DB name: " + name);
        SessionDB.createCatalog(name);
        if (SessionDB.hasCatalog(name)) {
            SessionDB.setJdbcCatalog(Globals.DB_PREFIX + name);
            return true;
        } else return false;
    }

    @FXML
    void menuCloseAct(ActionEvent event) {
        System.exit(0);

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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(PropsLoader.defaultDir));
        File file = fileChooser.showOpenDialog(null);
        try {
            if (PropsLoader.loadProps(file))
                FxDialogs.showInfo("Loading config.ini", "Config loaded from " + file.getAbsolutePath());
            else
                FxDialogs.showError("Loading config.ini", "Config failed to load from " + file.getAbsolutePath());
        } catch (NullPointerException ex) {
            FxDialogs.showException("titl", "msg", ex);
        }
        textFieldUser.setText(SessionDB.getUser());
        passwordField.setText(SessionDB.getPassword());
    }

    @FXML
    void menuSaveAct(ActionEvent event) {
        if (PropsLoader.saveProps())
            FxDialogs.showMessage("Saving config.ini", "Config saved to config.ini");
        else
            FxDialogs.showError("Saving config.ini", "Config failed to save to config.ini");
    }

    @FXML
    void menuTestAct(ActionEvent event) {
        SessionDB.setUser(textFieldUser.getText().trim());
        SessionDB.setPassword(passwordField.getText().trim());
        if (SessionDB.isValid()) {
            buttonEnter.setStyle("-fx-background-color: Yellow");
            System.out.println(SessionDB.numOfDBs());
            ArrayList<String> catalogList = SessionDB.listValidCatalogs();
            if (catalogList.size() == 0) {//TODO move db creation to DB section inside main prog
                if (!SessionDB.isRoot())
                    FxDialogs.showWarning("DB Wizard", "No valid DBs and no access to create.");
                else {
                    if (createDB()) {
                        FxDialogs.showInfo("DB Wizard", "DB creation Success");
                        System.out.println("Creation : " + SessionDB.createFullStructure());
                        if (SessionDB.isSchemaValid()) {
                            buttonEnter.setStyle("-fx-background-color: #75ff8a");
                            FxDialogs.showInfo("DB Wizard", "Structure creation Success");
                        } else {
                            buttonEnter.setStyle("-fx-background-color: Yellow");
                            FxDialogs.showWarning("DB Wizard", "Structure creation Fail");
                        }
                    } else {
                        FxDialogs.showError("DB Wizard", "DB Creation Fail");
                        buttonEnter.setStyle("-fx-background-color: red");
                    }
                }
            } else {
                String catalog = FxDialogs.showChoices("Pick a DB", "Pick a DB", "Pick a DB", "", catalogList);
                if (catalog != null && !catalog.equals("")) {
                    SessionDB.setJdbcCatalog(catalog);
                    if (SessionDB.isValid()) {
                        buttonEnter.setStyle("-fx-background-color: #75ff8a");
                    } else buttonEnter.setStyle("-fx-background-color: Yellow");
                } else buttonEnter.setStyle("-fx-background-color: Yellow");
            }
        } else buttonEnter.setStyle("-fx-background-color: red");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFieldUser.setText(SessionDB.getUser());
        passwordField.setText(SessionDB.getPassword());
    }

    @FXML
    public void buttonEnterAct(ActionEvent actionEvent) {
        SessionDB.setUser(textFieldUser.getText().trim());
        SessionDB.setPassword(passwordField.getText().trim());
        if (SessionDB.isValid()) {
            ArrayList<String> catalogList = SessionDB.listValidCatalogs();
            if (catalogList.size() > 0) {
                String catalog = FxDialogs.showChoices("Pick a DB", "Pick a DB", "Pick a DB", null, catalogList);
                if (catalog != null && !catalog.equals("")) {
                    SessionDB.setJdbcCatalog(catalog);
                    if (SessionDB.isValid()) {
                        Usuario user;
                        if (SessionDB.isRoot()) {
                            user = new Usuario(SessionDB.getUser(), SessionDB.getPassword(), "root", 1);
                        } else {
                            user = DataStore.getUsuarios().query("user", SessionDB.getUser());
                        }
                        DataStore.setUser(user);
                        System.out.println(DataStore.getUser().toString());
                        //Start
                        MainFX.getLoginStage().close();
                        MainFX.getMainStage().show();
                    } else FxDialogs.showError("Error", "Invalid Conn");//??
                }
            } else FxDialogs.showError("Error", "No Valid Catalog");
        } else FxDialogs.showError("Error", "Invalid Conn");
    }
}
