package app.control;

import app.control.main.CajaControl;
import app.control.table.CajasTableControl;
import app.control.table.CategoriasTableControl;
import app.control.table.CierreZTableControl;
import app.control.table.ComprasTableControl;
import app.control.table.ProductosTableControl;
import app.control.table.ProveedoresTableControl;
import app.control.table.SedesTableControl;
import app.control.table.SociosTableControl;
import app.control.table.TransferenciasTableControl;
import app.control.table.UsuariosTableControl;
import app.control.table.VentasTableControl;
import app.data.DataStore;
import app.misc.FXMLStage;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.StatusBar;

public class MainPaneControl extends BorderPane {

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
    @FXML
    public MenuItem fxMenuTransferencias;
    @FXML
    public MenuItem fxMenuStatus;
    public MenuItem fxMenuCierres;
    public MenuItem fxMenuCaja;
    public MenuItem fxMenuCajaDetail;
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
        return FXMLStage.getPane("/fxml/MainPane.fxml");
    }

    public void resetStatusPane() {
        Node pane = StatusControl.loadFXML();
        System.out.println(mainPane != null);
        System.out.println(pane != null);
        mainPane.setCenter(pane);
    }


    @FXML
    public void initialize() {
        resetStatusPane();

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

    @FXML
    public void fxMenuStatusAction(ActionEvent actionEvent) {
        resetStatusPane();
    }

    public void fxMenuCierreAction(ActionEvent actionEvent) {
        CierreZTableControl u = new CierreZTableControl();
        mainPane.setCenter(u);
    }

    public void fxMenuCajaAction(ActionEvent actionEvent) {
        CajaControl cajaControl = new CajaControl(DataStore.getSessionStore().getCaja());
        mainPane.setCenter(cajaControl);
    }

    public void fxMenuCajaDetailAction(ActionEvent actionEvent) {
    }
}
