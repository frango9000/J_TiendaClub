package app.control;

import app.control.table.CajasTableControl;
import app.control.table.CategoriasTableControl;
import app.control.table.ComprasTableControl;
import app.control.table.ProductosTableControl;
import app.control.table.ProveedoresTableControl;
import app.control.table.SedesTableControl;
import app.control.table.SociosTableControl;
import app.control.table.TransferenciasTableControl;
import app.control.table.UsuariosTableControl;
import app.control.table.VentasTableControl;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.StatusBar;

public class MainPaneControl {

    @FXML
    public MenuItem fxMenuUsuarios;
    @FXML
    public MenuItem fxMenuProveedores;
    @FXML
    public MenuItem fxMenuSocios;
    @FXML
    public MenuItem fxMenuProductos;
    @FXML
    public MenuItem fxMenuCategorias;
    @FXML
    public MenuItem fxMenuVentas;
    @FXML
    public MenuItem fxMenuCompras;
    public MenuItem fxMenuTransferencias;
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

    @FXML
    public void fxMenuProveedoresAction(ActionEvent actionEvent) {
        ProveedoresTableControl u = new ProveedoresTableControl();
        mainPane.setCenter(u);
    }

    @FXML
    public void fxMenuSociosAction(ActionEvent actionEvent) {
        SociosTableControl u = new SociosTableControl();
        mainPane.setCenter(u);
    }

    @FXML
    public void fxMenuCategoriasAction(ActionEvent actionEvent) {
        CategoriasTableControl u = new CategoriasTableControl();
        mainPane.setCenter(u);
    }

    @FXML
    public void fxMenuProductosAction(ActionEvent actionEvent) {
        ProductosTableControl u = new ProductosTableControl();
        mainPane.setCenter(u);
    }

    @FXML
    public void fxMenuVentasAction(ActionEvent actionEvent) {
        VentasTableControl u = new VentasTableControl();
        mainPane.setCenter(u);
    }

    @FXML
    public void fxMenuComprasAction(ActionEvent actionEvent) {
        ComprasTableControl u = new ComprasTableControl();
        mainPane.setCenter(u);
    }

    @FXML
    public void fxMenuTransferenciasAction(ActionEvent actionEvent) {
        TransferenciasTableControl u = new TransferenciasTableControl();
        mainPane.setCenter(u);
    }
}
