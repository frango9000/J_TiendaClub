package app.control.table;

import app.control.MainPaneControl;
import app.control.main.VentaControl;
import app.data.DataStore;
import app.model.Caja;
import app.model.Socio;
import app.model.Usuario;
import app.model.Venta;
import casteldao.datasource.DataSourceIdImpl;
import java.time.LocalDateTime;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class VentasTableControl extends TableControl<Venta> {


    private MenuItem menuItemVerVendidos = new MenuItem("Ver Vendidos");
    private MenuItem menuItemVerVentaUI = new MenuItem("Ver Venta");
    private MenuItem menuItemNuevaVentaUI = new MenuItem("Nueva Venta");

    {
        super.initialize();
        TableColumn<Venta, Usuario> fxColumnUsuario = new TableColumn<>("Usuario");
        fxColumnUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        fxTable.getColumns().add(fxColumnUsuario);

        TableColumn<Venta, Caja> fxColumnCaja = new TableColumn<>("Caja");
        fxColumnCaja.setCellValueFactory(new PropertyValueFactory<>("caja"));
        fxTable.getColumns().add(fxColumnCaja);

        TableColumn<Venta, Socio> fxColumnSocio = new TableColumn<>("Socio");
        fxColumnSocio.setCellValueFactory(new PropertyValueFactory<>("socio"));
        fxTable.getColumns().add(fxColumnSocio);

        TableColumn<Venta, LocalDateTime> fxColumnFechaHora = new TableColumn<>("Fecha");
        fxColumnFechaHora.setCellValueFactory(new PropertyValueFactory<>("fechahora"));
        fxTable.getColumns().add(fxColumnFechaHora);

        TableColumn<Venta, Integer> fxColumnTotal = new TableColumn<>("Total");
        fxColumnTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        fxTable.getColumns().add(fxColumnTotal);

        fxTable.setItems(listedObjects);

        fxBtnMenu.getItems().addAll(menuItemNuevaVentaUI, menuItemVerVentaUI, menuItemVerVendidos);
        menuItemVerVendidos.setOnAction(event -> menuItemVerVendidosAction(event));
        menuItemVerVentaUI.setOnAction(event -> menuItemVerVentaUIAction(event));
        menuItemNuevaVentaUI.setOnAction(event -> menuItemNuevaVentaUIAction(event));
    }

    public VentasTableControl() {
        addContent();
    }

    public VentasTableControl(Usuario usuario) {
        listedObjects.addAll(usuario.getVentas());
    }

    public VentasTableControl(Socio socio) {
        listedObjects.addAll(socio.getVentas());
    }

    public VentasTableControl(Caja caja) {
        listedObjects.addAll(caja.getVentas());
    }


    private void menuItemNuevaVentaUIAction(ActionEvent event) {
        VentaControl ventaControl = new VentaControl();
        MainPaneControl.setCenter(ventaControl);
    }

    private void menuItemVerVentaUIAction(ActionEvent event) {
        Venta venta = fxTable.getSelectionModel().getSelectedItem();
        if (venta != null) {
            VentaControl ventaControl = new VentaControl(venta);
            MainPaneControl.setCenter(ventaControl);
        }
    }

    private void menuItemVerVendidosAction(ActionEvent event) {
        Venta venta = fxTable.getSelectionModel().getSelectedItem();
        if (venta != null) {
            VendidosTableControl control = new VendidosTableControl(venta);
            MainPaneControl.setCenter(control);
        }
    }


    @Override
    protected String fxmlLocation() {
        return "/fxml/editor/VentaEditorGridPane.fxml";
    }

    @Override
    protected DataSourceIdImpl<Venta> getDataOrigin() {
        return DataStore.getSessionStore().getVentas();
    }

}
