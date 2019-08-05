package tiendaclub.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ConfigStage extends Stage {

    public ConfigStage() {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/ConfigPane.fxml"));
            setTitle("Config Screen");

            setScene(new Scene(root));
            setOnCloseRequest(event -> System.exit(0));
            root.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
