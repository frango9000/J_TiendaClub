package app.control.table;

import app.data.DataStore;
import app.data.casteldao.daomodel.IndexIdActiveDao;
import app.model.Proveedor;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProveedoresTableControl extends ActiveTableControl<Proveedor> {


    @Override
    public void initialize() {
        super.initialize();
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
        addContent();
    }

    @Override
    protected String fxmlLocation() {
        return "/fxml/editor/ProveedorEditorGridPane.fxml";
    }


    @Override
    protected IndexIdActiveDao<Proveedor> getDataOrigin() {
        return DataStore.getProveedores();
    }

}
