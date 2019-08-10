package tiendaclub.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.StatusBar;
import tiendaclub.control.table.SedesPaneControl;
import tiendaclub.control.table.UsuariosPaneControl;

import java.io.IOException;

public class MainPaneControl {

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

    public static Pane loadFXML() {
        String url = "/fxml/MainPane.fxml";
        Pane pane = null;
        try {
            pane = FXMLLoader.load(MainPaneControl.class.getResource(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }
    @FXML
    public void initialize() {

    }

    @FXML
    private void fxMenuUsuariosAction(ActionEvent actionEvent) throws IOException {
        Pane root = UsuariosPaneControl.getPane();
        mainPane.setCenter(root);
    }

    @FXML
    private void fxMenuSedesAction(ActionEvent actionEvent) throws IOException {
        Pane root = SedesPaneControl.getPane();
        mainPane.setCenter(root);
    }

    @FXML
    private void fxMenuCajasAction(ActionEvent actionEvent) {
    }
}
