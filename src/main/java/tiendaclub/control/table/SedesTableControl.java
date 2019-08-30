package tiendaclub.control.table;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import tiendaclub.data.DataStore;
import tiendaclub.data.casteldao.daomodel.IndexIdActiveDao;
import tiendaclub.model.Sede;

public class SedesTableControl extends ActiveTableControl<Sede> {


    @FXML
    void initialize() {
        super.initialize();
        TableColumn<Sede, String> fxColumnSede = new TableColumn<>("Sede");
        TableColumn<Sede, String> fxColumnTelefono = new TableColumn<>("Telefono");
        TableColumn<Sede, String> fxColumnDireccion = new TableColumn<>("Direccion");
        fxTable.getColumns().add(fxColumnSede);
        fxTable.getColumns().add(fxColumnTelefono);
        fxTable.getColumns().add(fxColumnDireccion);
        fxColumnSede.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        fxColumnTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        fxColumnDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        fxTable.setItems(listedObjects);

        addContent();
    }

    @Override
    protected String fxmlLocation() {
        return "/fxml/editor/SedeEditorGridPane.fxml";
    }

    @Override
    protected IndexIdActiveDao<Sede> getDataOrigin() {
        return DataStore.getSedes();
    }
}
