package tiendaclub.control.table;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import tiendaclub.control.editor.EditorControl;
import tiendaclub.control.editor.SedeEditorControl;
import tiendaclub.data.DataStore;
import tiendaclub.data.framework.dao.core.IndexIdActiveDao;
import tiendaclub.model.models.Sede;

public class SedesTableControl extends ActiveTableControl<Sede> {

    @FXML
    private TableColumn<Sede, String> fxColumnSede;
    @FXML
    private TableColumn<Sede, String> fxColumnTelefono;
    @FXML
    private TableColumn<Sede, String> fxColumnDireccion;


    @FXML
    void initialize() {

        fxColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        fxColumnSede.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        fxColumnTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        fxColumnDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        fxTable.setItems(listedObjects);

        addContent();
    }

    @Override
    protected IndexIdActiveDao<Sede> getDataOrigin() {
        return DataStore.getSedes();
    }

    @Override
    protected EditorControl<Sede> getEditorControl() {
        return SedeEditorControl.getPane();
    }
}
