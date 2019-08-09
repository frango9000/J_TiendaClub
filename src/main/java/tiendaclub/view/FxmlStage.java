package tiendaclub.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class FxmlStage extends Stage {

    public FxmlStage(String title) {
        setTitle(title);
        setOnCloseRequest(event -> System.exit(0));
    }

    public FxmlStage(String fxml, String title) {
        this(title);
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));

            setScene(new Scene(root));
            root.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FxmlStage(Pane root, String title) {
        this(title);
        setScene(new Scene(root));
        root.requestFocus();
    }


}
