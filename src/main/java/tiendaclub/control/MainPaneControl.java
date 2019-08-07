package tiendaclub.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import org.controlsfx.control.StatusBar;

import java.io.IOException;

public class MainPaneControl extends BorderPane {

    @FXML
    private MenuItem menuUsuarios;
    @FXML
    private StatusBar mainStatusBar;
    @FXML
    private BorderPane mainPane;

    public BorderPane getMainPane() {
        return mainPane;
    }

    @FXML
    public void initialize() {

    }

    @FXML
    private void menuUsuariosAct(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/UsuariosPane.fxml"));
        mainPane.setCenter(root);

    }
}
