package tiendaclub.control.table;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tiendaclub.data.DataStore;
import tiendaclub.model.models.Sede;
import tiendaclub.view.FXMLStage;

public class SedesPaneControl {

    final private ObservableList<Sede> sedes = FXCollections.observableArrayList();

    @FXML
    private TableView<Sede> fxTable;
    @FXML
    private TableColumn<Sede, Integer> fxColumnId;
    @FXML
    private TableColumn<Sede, String> fxColumnSede;
    @FXML
    private TableColumn<Sede, String> fxColumnTelefono;
    @FXML
    private TableColumn<Sede, String> fxColumnDireccion;

    public static Parent getRoot() {
        return FXMLStage.getRoot("/fxml/tables/SedesPane.fxml");
    }

    @FXML
    void initialize() {
        fxColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        fxColumnSede.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        fxColumnTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        fxColumnDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        fxTable.setItems(sedes);
        sedes.addAll(DataStore.getSedes().getCache().values());
    }

    @FXML
    void fxButtonAddAction(ActionEvent event) {

    }

    @FXML
    void fxButtonBackAction(ActionEvent event) {

    }

    @FXML
    void fxButtonDisableAction(ActionEvent event) {

    }

    @FXML
    void fxButtonEditAction(ActionEvent event) {

    }

    @FXML
    void fxButtonRefreshAction(ActionEvent event) {

    }

    @FXML
    void fxCheckHiddenAction(ActionEvent event) {

    }

}
