package tiendaclub.control.table;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import tiendaclub.control.editor.EditorControl;
import tiendaclub.data.framework.dao.ActivableDao;
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

        fxColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        fxColumnSede.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        fxColumnTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        fxColumnDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        fxTable.setItems(listedObjects);

        addContent();
    }


    @FXML
    protected void fxBtnAddAction(ActionEvent actionEvent) {

    }

    @FXML
    protected void fxBtnEditAction(ActionEvent actionEvent) {

    }

    @Override
    protected ActivableDao<Sede> getDataOrigin() {
        return null;
    }

    @Override
    protected Pane getEditorPane() throws IOException {
        return null;
    }

    @Override
    protected EditorControl<Sede> getEditorControl() {
        return null;
    }
}
