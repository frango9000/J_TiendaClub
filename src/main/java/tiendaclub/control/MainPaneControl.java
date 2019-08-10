package tiendaclub.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import org.controlsfx.control.StatusBar;
import tiendaclub.control.table.SedesPaneControl;
import tiendaclub.control.table.UsuariosPaneControl;

import java.io.IOException;

public class MainPaneControl extends BorderPane {

    @FXML
    private MenuItem menuUsuarios;
    @FXML
    private StatusBar mainStatusBar;
    @FXML
    private BorderPane mainPane;
    @FXML
    private MenuItem fxMenuSedes;
    @FXML
    private MenuItem fxMenuCajas;

    public BorderPane getMainPane() {
        return mainPane;
    }

    @FXML
    public void initialize() {

    }

    @FXML
    private void menuUsuariosAct(ActionEvent actionEvent) throws IOException {
        Parent root = UsuariosPaneControl.getRoot();
        mainPane.setCenter(root);
    }

    @FXML
    private void fxMenuSedesAction(ActionEvent actionEvent) throws IOException {
        Parent root = SedesPaneControl.getRoot();
        mainPane.setCenter(root);
    }

    @FXML
    private void fxMenuCajasAction(ActionEvent actionEvent) {
    }
}
