package tiendaclub;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tiendaclub.control.MainPaneControl;
import tiendaclub.control.PropsLoader;
import tiendaclub.data.DataStore;
import tiendaclub.data.SessionDB;
import tiendaclub.model.models.Caja;
import tiendaclub.model.models.Sede;
import tiendaclub.view.FxDialogs;
import tiendaclub.view.FxmlStage;

import java.util.Collection;

public class MainFX extends Application {

    private static Parent root;

    private static Stage mainStage;
    private static MainPaneControl mainPaneControl;


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

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void setMainStage(Stage mainStage) {
        MainFX.mainStage = mainStage;
    }

    public static MainPaneControl getMainPaneControl() {
        return mainPaneControl;
    }

    public static void setMainPaneControl(MainPaneControl mainPaneControl) {
        MainFX.mainPaneControl = mainPaneControl;
    }

    public static void initializeToolkit() {
        Platform.startup(() -> {
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;

        PropsLoader.loadProps();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainPane.fxml"));
        mainPaneControl = loader.getController();

        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));

        if (!SessionDB.isConnValid() || !PropsLoader.isQuickstart()) {
            Stage configStage = new FxmlStage("/fxml/ConfigPane.fxml", "Config Stage");
            configStage.showAndWait();
        }
        if (SessionDB.isSchemaValid()) {
            Stage loginStage = new FxmlStage("/fxml/LoginPane.fxml", "Login Stage");
            loginStage.showAndWait();

            DataStore.firstQuery();
            primaryStage.show();

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
