package app;

import app.control.MainPaneControl;
import app.control.PropsLoader;
import app.data.DataStore;
import app.data.SessionStore;
import app.data.casteldao.SessionDB;
import app.misc.FXMLStage;
import app.misc.FxDialogs;
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
        mainStage = primaryStage;
        PropsLoader.loadProps();

        Pane root = MainPaneControl.loadFXML();
        primaryStage.setScene(new Scene(root));

        if (!PropsLoader.isQuickstart() || !SessionDB.getSessionDB().isConnValid() ||
            !SessionDB.getSessionDB().isCatalogValid()) {
            Stage configStage = new FXMLStage("/fxml/ConfigPane.fxml", "Config Stage");
            configStage.setOnCloseRequest(event -> System.exit(0));
            configStage.showAndWait();
        }
        if (SessionDB.getSessionDB().isCatalogValid()) {
            DataStore.firstQuery();

            Stage loginStage = new FXMLStage("/fxml/LoginPane.fxml", "Login Stage");
            loginStage.setOnCloseRequest(event -> System.exit(0));
            loginStage.showAndWait();

            primaryStage.show();

            Set<Sede> sedes = DataStore.getSedes().getById().getCacheValues();
            if (sedes.size() == 0) {
                FxDialogs.showWarning("No Sede", "Debes crear una sede y una caja");
            } else {
                if (sedes.size() == 1) {
                    SessionStore.setSede(sedes.iterator().next());
                } else {
                    SessionStore.setSede(FxDialogs.showChoices("Sede:", "Sedes:", null, sedes));
                }
                Collection<Caja> cajas = DataStore.getCajas()
                                                  .getById()
                                                  .getCacheValues();//SessionStore.getSede().getCajas().values();
                if (cajas.size() == 0) {
                    FxDialogs.showWarning("No caja", "Debes crear una caja");
                } else if (cajas.size() == 1) {
                    SessionStore.setCaja(cajas.iterator().next());
                } else {
                    SessionStore.setCaja(FxDialogs.showChoices("Caja:", "Cajas:", null, cajas));
                }
            }
        }
    }
}
