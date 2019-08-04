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

    private static Stage loginStage;
    private static Stage mainStage;


    @Override
    public void start(Stage primaryStage) throws Exception {
        PropsLoader.loadProps();
        mainStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainPane.fxml"));
        primaryStage.setTitle("Main Screen");


        primaryStage.setScene(new Scene(root, 600, 400));
        root.requestFocus();
//        primaryStage.show();

        loginScreen();
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

    public void loginScreen() throws Exception {
        loginStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginPane.fxml"));
        loginStage.setTitle("Login Screen");


        loginStage.setScene(new Scene(root, 300, 300));
        root.requestFocus();
        loginStage.show();
    }


}
