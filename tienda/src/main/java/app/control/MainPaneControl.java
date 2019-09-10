package app.control;

import app.control.main.CajaControl;
import app.control.main.CompraControl;
import app.control.main.SedeControl;
import app.control.main.VentaControl;
import app.control.table.CajasTableControl;
import app.control.table.CategoriasTableControl;
import app.control.table.CierreZTableControl;
import app.control.table.CompradosTableControl;
import app.control.table.ComprasTableControl;
import app.control.table.ProductosTableControl;
import app.control.table.ProveedoresTableControl;
import app.control.table.SedesTableControl;
import app.control.table.SociosTableControl;
import app.control.table.TransferenciasTableControl;
import app.control.table.UsuariosTableControl;
import app.control.table.VendidosTableControl;
import app.control.table.VentasTableControl;
import app.data.DataStore;
import app.misc.FXMLStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.StatusBar;

public class MainPaneControl extends BorderPane {

    private static BorderPane staticMain;
    @FXML
    public MenuItem fxMenuProveedores;
    @FXML
    public MenuItem fxMenuEditorUsuarios;
    @FXML
    public MenuItem fxMenuEditorSocios;
    @FXML
    public MenuItem fxMenuEditorProductos;
    @FXML
    public MenuItem fxMenuEditorCategorias;
    @FXML
    public MenuItem fxMenuEditorVentas;
    @FXML
    public MenuItem fxMenuEditorCompras;
    @FXML
    public MenuItem fxMenuStatus;
    @FXML
    public MenuItem fxMenuEditorTransferencias;
    @FXML
    public MenuItem fxMenuEditorCierres;
    @FXML
    public MenuItem fxMenuCaja;
    @FXML
    public MenuItem fxMenuCajaDetail;
    @FXML
    public Menu fxMenuSede;
    @FXML
    public MenuItem fxMenuVerSede;
    @FXML
    public MenuItem fxMenuVerInventario;
    @FXML
    public MenuItem fxMenuNuevaCompra;
    @FXML
    public MenuItem fxMenuNuevaTransferencia;
    @FXML
    public MenuItem fxMenuVerCaja;
    @FXML
    public MenuItem fxMenuCajaNuevaVenta;
    @FXML
    public Menu fxMenuClientes;
    @FXML
    public MenuItem fxMenuNuevoSocio;
    @FXML
    public MenuItem fxMenuListaSocios;
    @FXML
    public MenuItem fxMenuNuevoProveedor;
    @FXML
    public MenuItem fxMenuListaProveedores;
    @FXML
    public Menu fxMenuEditores;
    @FXML
    public MenuItem fxMenuEditorSedes;
    @FXML
    public MenuItem fxMenuEditorCajas;
    @FXML
    public MenuItem fxMenuEditorProveedores;
    @FXML
    public MenuItem fxMenuEditorVendidos;
    @FXML
    public MenuItem fxMenuEditorComprados;
    @FXML
    private BorderPane mainPane;
    @FXML
    private StatusBar mainStatusBar;

    public static void setCenter(Pane pane) {
        staticMain.setCenter(pane);
    }

    public static Pane loadFXML() {
        return FXMLStage.getPane("/fxml/MainPane.fxml");
    }

    @FXML
    public void initialize() {
        staticMain = mainPane;
        resetStatusPane();
    }

    public void resetStatusPane() {
        Node pane = StatusControl.loadFXML();
        mainPane.setCenter(pane);
    }

    @FXML
    public void fxMenuCajaAction(ActionEvent actionEvent) {
        CajaControl cajaControl = new CajaControl(DataStore.getSessionStore().getCaja());
        mainPane.setCenter(cajaControl);
    }

    @FXML
    public void fxMenuCajaDetailAction(ActionEvent actionEvent) {
        CajaControl cajaControl = new CajaControl(DataStore.getSessionStore().getCaja().getLastCierreZ());
        mainPane.setCenter(cajaControl);
    }

    @FXML
    public void fxMenuListaProveedoresAction(ActionEvent actionEvent) {
    }

    @FXML
    public void fxMenuNuevoProveedorAction(ActionEvent actionEvent) {
    }

    @FXML
    public void fxMenuListaSociosAction(ActionEvent actionEvent) {
    }

    @FXML
    public void fxMenuNuevoSocioAction(ActionEvent actionEvent) {
    }

    @FXML
    public void fxMenuNuevaVentaAction(ActionEvent actionEvent) {
        VentaControl ventaControl = new VentaControl();
        mainPane.setCenter(ventaControl);
    }

    @FXML
    public void fxMenuNuevaTransferenciaAction(ActionEvent actionEvent) {
    }

    @FXML
    public void fxMenuNuevaCompraAction(ActionEvent actionEvent) {
        CompraControl compraControl = new CompraControl();
        mainPane.setCenter(compraControl);
    }

    @FXML
    public void fxMenuVerInventarioAction(ActionEvent actionEvent) {
    }

    @FXML
    public void fxMenuVerSedeAction(ActionEvent actionEvent) {
        SedeControl u = new SedeControl(DataStore.getSessionStore().getSede());
        mainPane.setCenter(u);
    }

    @FXML
    public void fxMenuExit(ActionEvent actionEvent) {
    }

    //Editor Menu Items
    @FXML
    private void fxMenuEditorUsuariosAction(ActionEvent actionEvent) {
        UsuariosTableControl u = new UsuariosTableControl();
        mainPane.setCenter(u);
    }
    @FXML
    private void fxMenuEditorSedesAction(ActionEvent actionEvent) {
        SedesTableControl u = new SedesTableControl();
        mainPane.setCenter(u);
    }
    @FXML
    private void fxMenuEditorCajasAction(ActionEvent actionEvent) {
        CajasTableControl u = new CajasTableControl();
        mainPane.setCenter(u);
    }
    @FXML
    public void fxMenuEditorProveedoresAction(ActionEvent actionEvent) {
        ProveedoresTableControl u = new ProveedoresTableControl();
        mainPane.setCenter(u);
    }
    @FXML
    public void fxMenuEditorSociosAction(ActionEvent actionEvent) {
        SociosTableControl u = new SociosTableControl();
        mainPane.setCenter(u);
    }
    @FXML
    public void fxMenuEditorCategoriasAction(ActionEvent actionEvent) {
        CategoriasTableControl u = new CategoriasTableControl();
        mainPane.setCenter(u);
    }
    @FXML
    public void fxMenuEditorProductosAction(ActionEvent actionEvent) {
        ProductosTableControl u = new ProductosTableControl();
        mainPane.setCenter(u);
    }
    @FXML
    public void fxMenuEditorVentasAction(ActionEvent actionEvent) {
        VentasTableControl u = new VentasTableControl();
        mainPane.setCenter(u);
    }
    @FXML
    public void fxMenuEditorComprasAction(ActionEvent actionEvent) {
        ComprasTableControl u = new ComprasTableControl();
        mainPane.setCenter(u);
    }
    @FXML
    public void fxMenuEditorTransferenciasAction(ActionEvent actionEvent) {
        TransferenciasTableControl u = new TransferenciasTableControl();
        mainPane.setCenter(u);
    }

    @FXML
    public void fxMenuEditorCierreAction(ActionEvent actionEvent) {
        CierreZTableControl u = new CierreZTableControl();
        mainPane.setCenter(u);
    }


    @FXML
    public void fxMenuStatusAction(ActionEvent actionEvent) {
        resetStatusPane();
    }

    public void fxMenuEditorVendidosAction(ActionEvent actionEvent) {
        VendidosTableControl u = new VendidosTableControl();
        mainPane.setCenter(u);
    }

    public void fxMenuEditorCompradosAction(ActionEvent actionEvent) {
        CompradosTableControl u = new CompradosTableControl();
        mainPane.setCenter(u);
    }
}
