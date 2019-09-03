package app.control.table;

import app.data.DataStore;
import app.model.Sede;
import casteldao.dao.DataSourceIdActive;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

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
    protected DataSourceIdActive<Sede> getDataOrigin() {
        return DataStore.getSedes();
    }
}
