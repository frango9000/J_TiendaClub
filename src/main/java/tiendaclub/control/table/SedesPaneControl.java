package tiendaclub.control.table;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import tiendaclub.data.DataStore;
import tiendaclub.data.framework.dao.core.IndexIdActiveDao;
import tiendaclub.model.models.Sede;
import tiendaclub.view.FXMLStage;

public class SedesPaneControl extends ActiveTableControl<Sede> {

    @FXML
    private TableColumn<Sede, String> fxColumnSede;
    @FXML
    private TableColumn<Sede, String> fxColumnTelefono;
    @FXML
    private TableColumn<Sede, String> fxColumnDireccion;

    public static Pane getPane() {
        return FXMLStage.getPane("/fxml/tables/SedesPane.fxml");
    }

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
    protected Pane getEditorPane() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editor/SedeEditorPane.fxml"));
        Pane pane = loader.load();
        editorControl = loader.getController();
        return pane;
    }
}
