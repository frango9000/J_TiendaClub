package app.control.table;

import app.control.MainPaneControl;
import app.control.main.CompraControl;
import app.data.DataStore;
import app.model.Compra;
import app.model.Proveedor;
import app.model.Sede;
import app.model.Socio;
import app.model.Usuario;
import casteldao.datasource.DataSourceIdImpl;
import java.time.LocalDateTime;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class ComprasTableControl extends TableControl<Compra> {


    private MenuItem menuItemVerComprados = new MenuItem("Ver Comprados");
    private MenuItem menuItemVerCompraUI = new MenuItem("Ver Compra");
    private MenuItem menuItemNuevaCompraUI = new MenuItem("Nueva Compra");
    {
        TableColumn<Compra, Usuario> fxColumnUsuario = new TableColumn<>("Usuario");
        fxColumnUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        fxTable.getColumns().add(fxColumnUsuario);

        TableColumn<Compra, Sede> fxColumnSede = new TableColumn<>("Sede");
        fxColumnSede.setCellValueFactory(new PropertyValueFactory<>("sede"));
        fxTable.getColumns().add(fxColumnSede);

        TableColumn<Compra, Socio> fxColumnProveedor = new TableColumn<>("Proveedor");
        fxColumnProveedor.setCellValueFactory(new PropertyValueFactory<>("proveedor"));
        fxTable.getColumns().add(fxColumnProveedor);

        TableColumn<Compra, LocalDateTime> fxColumnFechaHora = new TableColumn<>("Fecha");
        fxColumnFechaHora.setCellValueFactory(new PropertyValueFactory<>("fechahora"));
        fxTable.getColumns().add(fxColumnFechaHora);

        fxTable.setItems(listedObjects);
        fxBtnMenu.getItems().addAll(menuItemNuevaCompraUI, menuItemVerCompraUI, menuItemVerComprados);
        menuItemVerComprados.setOnAction(event -> menuItemVerCompradosAction(event));
        menuItemVerCompraUI.setOnAction(event -> menuItemVerCompraUIAction(event));
        menuItemNuevaCompraUI.setOnAction(event -> menuItemNuevaCompraUIAction(event));
    }

    public ComprasTableControl() {
        addContent();
    }

    public ComprasTableControl(Usuario usuario) {
        listedObjects.addAll(usuario.getCompras());
    }

    public ComprasTableControl(Sede sede) {
        listedObjects.addAll(sede.getCompras());
    }

    public ComprasTableControl(Proveedor proveedor) {
        listedObjects.addAll(proveedor.getCompras());
    }

    private void menuItemNuevaCompraUIAction(ActionEvent event) {
        CompraControl control = new CompraControl();
        MainPaneControl.setCenter(control);
    }

    private void menuItemVerCompraUIAction(ActionEvent event) {
        Compra compra = fxTable.getSelectionModel().getSelectedItem();
        if (compra != null) {
            CompraControl control = new CompraControl(compra);
            MainPaneControl.setCenter(control);
        }
    }

    private void menuItemVerCompradosAction(ActionEvent event) {
        Compra compra = fxTable.getSelectionModel().getSelectedItem();
        if (compra != null) {
            CompradosTableControl control = new CompradosTableControl(compra);
            MainPaneControl.setCenter(control);
        }
    }
    @Override
    protected String fxmlLocation() {
        return "/fxml/editor/CompraEditorGridPane.fxml";
    }

    @Override
    protected DataSourceIdImpl<Compra> getDataOrigin() {
        return DataStore.getSessionStore().getCompras();
    }
}
