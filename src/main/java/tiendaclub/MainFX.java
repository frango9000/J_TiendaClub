package tiendaclub;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tiendaclub.control.PropsLoader;
import tiendaclub.data.DataStore;
import tiendaclub.data.SessionDB;
import tiendaclub.model.models.Caja;
import tiendaclub.model.models.Sede;
import tiendaclub.view.ConfigStage;
import tiendaclub.view.FxDialogs;
import tiendaclub.view.LoginStage;
import tiendaclub.view.MainStage;

import java.util.Collection;

public class MainFX extends Application {

    private static Parent root;

    private static Stage configStage;
    private static Stage loginStage;
    private static Stage mainStage;


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

    public static Parent getRoot() {
        return root;
    }

    public static void setRoot(Parent node) {
        root = node;
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

            Collection<Sede> sedes = DataStore.getSedes().getCache().values();
            if (sedes.size() == 0)
                FxDialogs.showWarning("No Sede", "Debes crear una sede");
            else if (sedes.size() == 1)
                DataStore.setSede(sedes.iterator().next());
            else
                DataStore.setSede(FxDialogs.showChoices("Sede:", "Sedes:", null, sedes));

            Collection<Caja> cajas = DataStore.getSede().getCajas().values();
            if (cajas.size() == 0)
                FxDialogs.showWarning("No caja", "Debes crear una caja");
            else if (cajas.size() == 1)
                DataStore.setCaja(cajas.iterator().next());
            else
                DataStore.setCaja(FxDialogs.showChoices("Caja:", "Cajas:", null, cajas));
        }
    }

    public void go() {
        launch("");
    }

    public void go(Parent node) {
        setRoot(node);
        go();
    }
}
