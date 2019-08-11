package tiendaclub.control.table;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import tiendaclub.data.DataStore;
import tiendaclub.model.models.Sede;
import tiendaclub.view.FXMLStage;

import java.io.IOException;

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
        dataOrigin = DataStore.getSedes();

        fxColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        fxColumnSede.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        fxColumnTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        fxColumnDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        fxTable.setItems(listedObjects);
        listedObjects.addAll(DataStore.getSedes().getCache().values());
    }


    @FXML
    protected void fxBtnAddAction(ActionEvent actionEvent) throws IOException {

    }

    @FXML
    protected void fxBtnEditAction(ActionEvent actionEvent) throws IOException {

    }
}
