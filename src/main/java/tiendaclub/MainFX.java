package tiendaclub;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tiendaclub.control.PropsLoader;

public class MainFX extends Application {

    private static Parent root;

    @Override
    public void start(Stage primaryStage) throws Exception {

        PropsLoader.loadProps();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginPane.fxml"));
        primaryStage.setTitle("MainFX Test");


        primaryStage.setScene(new Scene(root, 300, 300));
        root.requestFocus();
        primaryStage.show();
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
