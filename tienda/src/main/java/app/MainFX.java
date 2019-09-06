package app;

import app.control.MainPaneControl;
import app.data.DataStore;
import app.data.SessionDB;
import app.misc.FXMLStage;
import app.misc.FxDialogs;
import app.misc.Globals;
import app.misc.PropsLoader;
import app.model.Caja;
import app.model.Sede;
import java.util.Collection;
import java.util.Set;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainFX extends Application {


    private static Stage mainStage;

    public static void main(String[] args) {
        launch(args);

    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void setMainStage(Stage mainStage) {
        MainFX.mainStage = mainStage;
    }

    public static void initializeToolkit() {
        Platform.startup(() -> {
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        SessionDB sessionDB = SessionDB.getSession();
        mainStage = primaryStage;
        PropsLoader.loadProps();

        Pane root = MainPaneControl.loadFXML();
        primaryStage.setScene(new Scene(root));

        if (!PropsLoader.isQuickstart() || !sessionDB.isLinkValid() || !sessionDB.isCatalogValid(Globals.CATALOG_MODEL)) {
            Stage configStage = new FXMLStage("/fxml/ConfigPane.fxml", "Config Stage");
            configStage.setOnCloseRequest(event -> System.exit(0));
            configStage.showAndWait();
        }
        if (sessionDB.isCatalogValid(Globals.CATALOG_MODEL)) {
            DataStore sessionStore = DataStore.getSessionStore();
            sessionStore.firstQuery();

            Stage loginStage = new FXMLStage("/fxml/LoginPane.fxml", "Login Stage");
            loginStage.setOnCloseRequest(event -> System.exit(0));
            loginStage.showAndWait();

            primaryStage.show();

            Set<Sede> sedes = sessionStore.getSedes().getById().getCacheValues();
            if (sedes.size() == 0) {
                FxDialogs.showWarning("No Sede", "Debes crear una sede y una caja");
            } else {
                if (sedes.size() == 1) {
                    sessionStore.setSede(sedes.iterator().next());
                } else {
                    sessionStore.setSede(FxDialogs.showChoices("Sede:", "Sedes:", null, sedes));
                }
                Collection<Caja> cajas = sessionStore.getCajas().getIndexSede().getCacheKeyValues(sessionStore.getSede());
                if (cajas.size() == 0) {
                    FxDialogs.showWarning("No caja", "Debes crear una caja");
                } else if (cajas.size() == 1) {
                    sessionStore.setCaja(cajas.iterator().next());
                } else {
                    sessionStore.setCaja(FxDialogs.showChoices("Caja:", "Cajas:", null, cajas));
                }
            }
        }
    }
}
