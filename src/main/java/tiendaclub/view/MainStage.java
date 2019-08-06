package tiendaclub.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainStage extends Stage {

    public MainStage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainPane.fxml"));
            setTitle("Main Screen");


            setScene(new Scene(root));
            root.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
