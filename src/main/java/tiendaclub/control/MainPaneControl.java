package tiendaclub.control;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.StatusBar;
import tiendaclub.control.table.CajasTableControl;
import tiendaclub.control.table.ProveedoresTableControl;
import tiendaclub.control.table.SedesTableControl;
import tiendaclub.control.table.SociosTableControl;
import tiendaclub.control.table.UsuariosTableControl;

public class MainPaneControl {

    public MenuItem fxMenuUsuarios;
    public MenuItem fxMenuProveedores;
    public MenuItem fxMenuSocios;
    public MenuItem fxMenuProductos;
    public MenuItem fxMenuCategorias;
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
        CajasTableControl u = new CajasTableControl();
        mainPane.setCenter(u);
    }

    public void fxMenuProveedoresAction(ActionEvent actionEvent) {
        ProveedoresTableControl u = new ProveedoresTableControl();
        mainPane.setCenter(u);
    }

    public void fxMenuSociosAction(ActionEvent actionEvent) {
        SociosTableControl u = new SociosTableControl();
        mainPane.setCenter(u);
    }

    public void fxMenuCategoriasAction(ActionEvent actionEvent) {
    }

    public void fxMenuProductosAction(ActionEvent actionEvent) {
    }
}
