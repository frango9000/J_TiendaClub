package tiendaclub.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class FXMLStage extends Stage {

    public FXMLStage(String title) {
        setTitle(title);
    }

    public FXMLStage(String fxml, String title) {
        this(title);
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));

            setScene(new Scene(root));
            root.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FXMLStage(Pane root, String title) {
        this(title);
        setScene(new Scene(root));
        root.requestFocus();
    }

    public static Parent getRoot(String url) {
        Parent root = null;
        try {
            root = FXMLLoader.load(FXMLStage.class.getResource(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }
}
