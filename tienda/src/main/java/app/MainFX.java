package app;

import app.control.MainPaneControl;
import app.data.DataStore;
import app.data.SessionDB;
import app.misc.FXMLStage;
import app.misc.Globals;
import app.misc.PropsLoader;
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
        }
    }
}
