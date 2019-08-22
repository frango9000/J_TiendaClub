package tiendaclub.control;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.StatusBar;
import tiendaclub.control.table.SedesTableControl;
import tiendaclub.control.table.UsuariosTableControl;

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

    public BorderPane getMainPane() {
        return mainPane;
    }

    @FXML
    public void initialize() {

    }

    @FXML
    private void fxMenuUsuariosAction(ActionEvent actionEvent) throws IOException {
        //        Pane root = UsuariosPaneControl.getPane();
        UsuariosTableControl u = new UsuariosTableControl();
        mainPane.setCenter(u);
    }

    @FXML
    private void fxMenuSedesAction(ActionEvent actionEvent) throws IOException {
        SedesTableControl u = new SedesTableControl();
        mainPane.setCenter(u);
    }

    @FXML
    private void fxMenuCajasAction(ActionEvent actionEvent) {
    }
}
