package tiendaclub.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import org.controlsfx.control.StatusBar;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPaneControl implements Initializable {

    @FXML
    private MenuItem menuUsuarios;
    @FXML
    private StatusBar mainStatusBar;
    @FXML
    private BorderPane mainPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void menuUsuariosAct(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/UsuariosPane.fxml"));
        mainPane.setCenter(root);

    }
}
