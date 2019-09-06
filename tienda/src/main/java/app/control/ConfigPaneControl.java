package app.control;

import app.MainFX;
import app.data.SessionDB;
import app.misc.FxDialogs;
import app.misc.Globals;
import app.misc.PropsLoader;
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
        fxUsername.setText(SessionDB.getSession().getJdbcUser().trim());
        fxPassword.setText(SessionDB.getSession().getJdbcPassword().trim());
        fxDbIp.setText(SessionDB.getSession().getJdbcIP().trim());
        fxDbPort.setText(SessionDB.getSession().getJdbcPort().trim());
        fxDbCatalog.setText(SessionDB.getSession().getJdbcCatalog().trim());
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
        if (SessionDB.getSession().isLinkValid()) {
            ArrayList<String> catalogList = SessionDB.getSession().listValidCatalogs(Globals.CATALOG_MODEL);
            if (catalogList.size() > 0) {
                String catalog = FxDialogs.showChoices("Pick a DB", "Pick a DB", fxDbCatalog.getText(), catalogList);
                if (catalog != null && !catalog.equals("")) {
                    SessionDB.getSession().setJdbcCatalog(catalog);
                    if (SessionDB.getSession().isSessionValid()) {
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
        SessionDB sessionDB = SessionDB.getSession();
        sessionDB.setJdbcIP(fxDbIp.getText().trim());
        sessionDB.setJdbcPort(fxDbPort.getText().trim());
        sessionDB.setJdbcCatalog(fxDbCatalog.getText().trim());
        sessionDB.setJdbcUser(fxUsername.getText().trim());
        sessionDB.setJdbcPassword(fxPassword.getText().trim());
        if (sessionDB.isLinkValid()) {
            fxBtnTestConn.setStyle("-fx-background-color: #75ff8a");
        } else {
            fxBtnTestConn.setStyle("-fx-background-color: red");
        }
    }

    @FXML
    private void fxBtnTestCatalogAction(ActionEvent actionEvent) {
        SessionDB sessionDB = SessionDB.getSession();
        sessionDB.setJdbcIP(fxDbIp.getText().trim());
        sessionDB.setJdbcPort(fxDbPort.getText().trim());
        sessionDB.setJdbcCatalog(fxDbCatalog.getText().trim());
        sessionDB.setJdbcUser(fxUsername.getText().trim());
        sessionDB.setJdbcPassword(fxPassword.getText().trim());
        ArrayList<String> listCatalogs = sessionDB.listPrefixedCatalogs(Globals.DB_PREFIX);
        if (listCatalogs.size() == 0) {//TODO move db creation to DB section inside main prog
            if (!sessionDB.isRoot()) {
                FxDialogs.showWarning("Catalog Wizard", "No valid Catalogs and no access to create.");
            } else {
                createCatalog(listCatalogs);
            }
        } else {
            String catalog = FxDialogs.showChoices("Pick a DB", "Pick a DB", "Pick a DB", null, listCatalogs);
            if (catalog != null && !catalog.equals("")) {
                if (sessionDB.isCatalogValid(catalog)) {
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
        SessionDB sessionDB = SessionDB.getSession();
        String catalog = "";
        do {
            catalog = FxDialogs.showTextInput("Catalog Name", "Enter new Catalog name").trim();
        } while (catalog.length() < 1 || listCatalogs.contains(Globals.DB_PREFIX + catalog));
        boolean success = SessionDB.getSession().createCatalog(Globals.DB_PREFIX + catalog);
        System.out.println("# Create catalog OK: " + success);
        success = success && sessionDB.hasCatalog(catalog);
        System.out.println("# Has catalog: " + success);
        if (success) {
            sessionDB.setJdbcCatalog(Globals.DB_PREFIX + catalog);
            System.out.println("# " + sessionDB.getJdbcCatalog());
            int n = sessionDB.createFullStructure();
            System.out.println("# Creation n: " + n);
            FxDialogs.showInfo("DB Wizard", "Building Catalog " + ((n == 16) ? "Success" : "Fail"));
            boolean valid = sessionDB.isCatalogValid(Globals.CATALOG_MODEL);
            if (valid) {
                fxBtnTestCatalog.setStyle("-fx-background-color: #75ff8a");
                fxDbCatalog.setText(Globals.DB_PREFIX + catalog);
            } else {
                fxBtnTestCatalog.setStyle("-fx-background-color: Yellow");
                sessionDB.dropCatalog(Globals.DB_PREFIX + catalog);
                FxDialogs.showWarning("DB Wizard", "Catalog invalid");
            }
        } else {
            FxDialogs.showError("DB Wizard", "Catalog Creation Fail");
            fxBtnTestCatalog.setStyle("-fx-background-color: red");
        }
    }

    @FXML
    private void fxHiddenCreateCatalog(ActionEvent actionEvent) {
        createCatalog(SessionDB.getSession().listCatalogs());
    }

    @FXML
    private void fxHiddenDropCatalog(ActionEvent actionEvent) {
        String catalog = FxDialogs.showChoices("Pick a DB", "Pick a DB", "Pick a DB", "", SessionDB.getSession().listCatalogs());
        if (catalog != null && catalog.length() > 1 && FxDialogs.showConfirmBoolean(
            "Droping Catalog: " + catalog, "This is irreversible, data will be lost.")) {
            SessionDB.getSession().dropCatalog(catalog);
        }
    }
}
