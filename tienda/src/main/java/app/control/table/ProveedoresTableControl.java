package app.control.table;

import app.control.MainPaneControl;
import app.data.DataStore;
import app.model.Proveedor;
import casteldao.datasource.DataSourceIdActive;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProveedoresTableControl extends ActiveTableControl<Proveedor> {


    private MenuItem menuItemVerCompras = new MenuItem("Ver Compras");

    {
        TableColumn<Proveedor, String> fxColumnNif = new TableColumn<>("NIF");
        fxTable.getColumns().add(fxColumnNif);
        fxColumnNif.setCellValueFactory(new PropertyValueFactory<Proveedor, String>("nif"));

        TableColumn<Proveedor, String> fxColumnNombre = new TableColumn<>("Nombre");
        fxTable.getColumns().add(fxColumnNombre);
        fxColumnNombre.setCellValueFactory(new PropertyValueFactory<Proveedor, String>("nombre"));

        TableColumn<Proveedor, String> fxColumnTelefono = new TableColumn<>("Telefono");
        fxTable.getColumns().add(fxColumnTelefono);
        fxColumnTelefono.setCellValueFactory(new PropertyValueFactory<Proveedor, String>("telefono"));

        TableColumn<Proveedor, String> fxColumnEmail = new TableColumn<>("Email");
        fxTable.getColumns().add(fxColumnEmail);
        fxColumnEmail.setCellValueFactory(new PropertyValueFactory<Proveedor, String>("email"));

        fxTable.setItems(listedObjects);
        menuItemVerCompras.setOnAction(event -> menuItemVerComprasAction(event));
    }

    public ProveedoresTableControl() {
        addContent();
    }

    private void menuItemVerComprasAction(ActionEvent event) {
        Proveedor selected = fxTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            ComprasTableControl control = new ComprasTableControl(selected);
            MainPaneControl.setCenter(control);
        }
    }
    @Override
    protected String fxmlLocation() {
        return "/fxml/editor/ProveedorEditorGridPane.fxml";
    }


    @Override
    protected DataSourceIdActive<Proveedor> getDataOrigin() {
        return DataStore.getSessionStore().getProveedores();
    }

}
