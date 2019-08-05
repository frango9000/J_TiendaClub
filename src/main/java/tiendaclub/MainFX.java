package tiendaclub;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tiendaclub.control.PropsLoader;
import tiendaclub.data.DataStore;
import tiendaclub.model.SessionDB;
import tiendaclub.view.ConfigStage;
import tiendaclub.view.FxDialogs;
import tiendaclub.view.LoginStage;
import tiendaclub.view.MainStage;

public class MainFX extends Application {

    private static Parent root;

    private static Stage configStage;
    private static Stage loginStage;
    private static Stage mainStage;


    @Override
    public void start(Stage primaryStage) throws Exception {
        PropsLoader.loadProps();
        mainStage = primaryStage;

        if (!SessionDB.isConnValid() || !PropsLoader.isQuickstart()) {
            configStage = new ConfigStage();
            configStage.showAndWait();
        }
        if (SessionDB.isSchemaValid()) {
            loginStage = new LoginStage();
            loginStage.showAndWait();

            DataStore.firstQuery();
            mainStage = new MainStage();
            mainStage.show();

            MainStage.setSede(FxDialogs.showChoices("Sede:", "Sede:", null, DataStore.getSedes().getCache().values()));
            MainStage.setCaja(FxDialogs.showChoices("Caja:", "Caja", null, MainStage.getSede().getCajas().values()));
        }
    }

    public MainFX() {
        if (root == null)
            //pane = new Pane(new Label("Default Pane"));
            root = new Pane();
        //pane = new RawEditorPane();
    }

    public MainFX(Parent node) {
        root = node;
    }

    public static void main(String[] args) {
        launch(args);

    }

    public static void setRoot(Parent node) {
        root = node;
    }

    public static Parent getRoot() {
        return root;
    }

    public static Stage getLoginStage() {
        return loginStage;
    }

    public static void setLoginStage(Stage loginStage) {
        MainFX.loginStage = loginStage;
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void setMainStage(Stage mainStage) {
        MainFX.mainStage = mainStage;
    }

    public static Stage getConfigStage() {
        return configStage;
    }

    public static void setConfigStage(Stage configStage) {
        MainFX.configStage = configStage;
    }

    public static void initializeToolkit() {
        Platform.startup(() -> {
        });
    }

    public void go() {
        launch("");
    }

    public void go(Parent node) {
        setRoot(node);
        go();
    }
}
