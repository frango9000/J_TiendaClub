package tiendaclub.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginStage extends Stage {
    public LoginStage() {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/LoginPane.fxml"));
            setTitle("Login Screen");

            setScene(new Scene(root));
            setOnCloseRequest(event -> System.exit(0));
            root.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
