package app.control;

import app.MainFX;
import app.data.casteldao.SessionDB;
import app.misc.FxDialogs;
import app.misc.Globals;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class ConfigPaneControl extends VBox {

    @FXML
    private TextField fxUsername;
    @FXML
    private PasswordField fxPassword;
    @FXML
    private TextField fxDbIp;
    @FXML
    private TextField fxDbPort;
    @FXML
    private Button fxBtnTestConn;
    @FXML
    private Button fxBtnTestCatalog;
    @FXML
    private TextField fxDbCatalog;
    @FXML
    private TitledPane fxTitledPane;

    @FXML
    public void initialize() {
        setJdbcFields();
    }


    private void setJdbcFields() {
        fxUsername.setText(SessionDB.getSessionDB().getJdbcUser().trim());
        fxPassword.setText(SessionDB.getSessionDB().getJdbcPassword());
        fxDbIp.setText(SessionDB.getSessionDB().getJdbcIP().trim());
        fxDbPort.setText(SessionDB.getSessionDB().getJdbcPort().trim());
        fxDbCatalog.setText(SessionDB.getSessionDB().getJdbcCatalog());
    }

    @FXML
    void menuCloseAct(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void fxMenuLoadAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        URI def = PropsLoader.getDefaultFile();
        if (def != null)
            fileChooser.setInitialDirectory(new File(def));
        File file = fileChooser.showOpenDialog(MainFX.getMainStage());
        try {
            if (PropsLoader.loadProps(file)) {
                FxDialogs.showInfo("Loading config.ini", "Config loaded from " + file.getAbsolutePath());
            } else {
                FxDialogs.showError("Loading config.ini", "Config failed to load from " + file.getAbsolutePath());
            }
        } catch (NullPointerException ex) {
            FxDialogs.showException("titl", "msg", ex);
        }
        setJdbcFields();
    }

    @FXML
    void fxMenuSaveAction(ActionEvent event) {
        if (PropsLoader.saveProps()) {
            FxDialogs.showMessage("Saving config.ini", "Config saved to config.ini");
        } else {
            FxDialogs.showError("Saving config.ini", "Config failed to save to config.ini");
        }
    }

    @FXML
    public void fxBtnEnterAction(ActionEvent actionEvent) {
        setJdbcFields();
        if (SessionDB.getSessionDB().isConnValid()) {
            ArrayList<String> catalogList = SessionDB.getSessionDB().listValidCatalogs();
            if (catalogList.size() > 0) {
                String catalog = FxDialogs.showChoices("Pick a DB", "Pick a DB", fxDbCatalog.getText(), catalogList);
                if (catalog != null && !catalog.equals("")) {
                    SessionDB.getSessionDB().setJdbcCatalog(catalog);
                    if (SessionDB.getSessionDB().isSessionValid()) {
                        //Start
                        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
                    } else {
                        FxDialogs.showError("Error", "Invalid Conn");//??
                    }
                }
            } else {
                FxDialogs.showError("Error", "No Valid Catalog");
            }
        } else {
            FxDialogs.showError("Error", "Invalid Conn");
        }
    }

    @FXML
    private void fxBtnTestConnAction(ActionEvent actionEvent) {
        SessionDB.getSessionDB()
                 .setValues(fxDbIp.getText().trim(), fxDbPort.getText().trim(), fxDbCatalog.getText().trim(), fxUsername
            .getText()
            .trim(), fxPassword.getText().trim());
        if (SessionDB.getSessionDB().isConnValid()) {
            fxBtnTestConn.setStyle("-fx-background-color: #75ff8a");
        } else {
            fxBtnTestConn.setStyle("-fx-background-color: red");
        }
    }

    @FXML
    private void fxBtnTestCatalogAction(ActionEvent actionEvent) {
        SessionDB.getSessionDB()
                 .setValues(fxDbIp.getText().trim(), fxDbPort.getText().trim(), fxDbCatalog.getText().trim(), fxUsername
            .getText()
            .trim(), fxPassword.getText().trim());
        ArrayList<String> listCatalogs = SessionDB.getSessionDB().listCatalogs();
        if (listCatalogs.size() == 0) {//TODO move db creation to DB section inside main prog
            if (!SessionDB.getSessionDB().isRoot()) {
                FxDialogs.showWarning("Catalog Wizard", "No valid Catalogs and no access to create.");
            } else {
                createCatalog(listCatalogs);
            }
        } else {
            String catalog = FxDialogs.showChoices("Pick a DB", "Pick a DB", "Pick a DB", null, listCatalogs);
            if (catalog != null && !catalog.equals("")) {
                if (SessionDB.getSessionDB().isCatalogValid(catalog)) {
                    fxBtnTestCatalog.setStyle("-fx-background-color: #75ff8a");
                    fxDbCatalog.setText(catalog);
                } else {
                    fxBtnTestCatalog.setStyle("-fx-background-color: Yellow");
                }
            } else {
                fxBtnTestCatalog.setStyle("-fx-background-color: Red");
            }
        }
    }

    private void createCatalog(ArrayList<String> listCatalogs) {
        String catalog = "";
        do {
            catalog = FxDialogs.showTextInput("Catalog Name", "Enter new Catalog name").trim();
        } while (catalog.length() < 1 || listCatalogs.contains(Globals.DB_PREFIX + catalog));
        boolean success = SessionDB.getSessionDB().createCatalog(catalog);
        System.out.println("# Create catalog OK: " + success);
        success = success && SessionDB.getSessionDB().hasCatalog(catalog);
        System.out.println("# Has catalog: " + success);
        if (success) {
            SessionDB.getSessionDB().setJdbcCatalog(Globals.DB_PREFIX + catalog);
            System.out.println("# " + SessionDB.getSessionDB().getJdbcCatalog());
            int n = SessionDB.getSessionDB().createFullStructure();
            System.out.println("# Creation n: " + n);
            FxDialogs.showInfo("DB Wizard", "Building Catalog " + ((n == 16) ? "Success" : "Fail"));
            boolean valid = SessionDB.getSessionDB().isCatalogValid();
            if (valid) {
                fxBtnTestCatalog.setStyle("-fx-background-color: #75ff8a");
                fxDbCatalog.setText(Globals.DB_PREFIX + catalog);
            } else {
                fxBtnTestCatalog.setStyle("-fx-background-color: Yellow");
                SessionDB.getSessionDB().dropCatalog(Globals.DB_PREFIX + catalog);
                FxDialogs.showWarning("DB Wizard", "Catalog invalid");
            }
        } else {
            FxDialogs.showError("DB Wizard", "Catalog Creation Fail");
            fxBtnTestCatalog.setStyle("-fx-background-color: red");
        }
    }

    @FXML
    private void fxHiddenCreateCatalog(ActionEvent actionEvent) {
        createCatalog(SessionDB.getSessionDB().listCatalogs());
    }

    @FXML
    private void fxHiddenDropCatalog(ActionEvent actionEvent) {
        String catalog = FxDialogs.showChoices("Pick a DB", "Pick a DB", "Pick a DB", "", SessionDB.getSessionDB()
                                                                                                   .listCatalogs());
        if (catalog != null && catalog.length() > 1 && FxDialogs.showConfirmBoolean(
            "Droping Catalog: " + catalog, "This is irreversible, data will be lost.")) {
            SessionDB.getSessionDB().dropCatalog(catalog);
        }
    }
}
