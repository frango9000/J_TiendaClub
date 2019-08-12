package tiendaclub.control.table;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import tiendaclub.control.editor.EditorControl;
import tiendaclub.data.DataStore;
import tiendaclub.data.framework.dao.ActivableDao;
import tiendaclub.model.models.Acceso;
import tiendaclub.model.models.Usuario;
import tiendaclub.view.FXMLStage;

import java.io.IOException;

public class UsuariosPaneControl extends ActiveTableControl<Usuario> {

    private EditorControl<Usuario> control;
    @FXML
    private TableColumn<Usuario, String> fxColumnUser;
    @FXML
    private TableColumn<Usuario, String> fxColumnName;
    @FXML
    private TableColumn<Usuario, Acceso> fxColumnLevel;

    public static Pane getPane() {
        return FXMLStage.getPane("/fxml/tables/UsuariosPane.fxml");
    }

    @FXML
    public void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader();

        fxColumnId.setCellValueFactory(new PropertyValueFactory<Usuario, Integer>("id"));
        fxColumnUser.setCellValueFactory(new PropertyValueFactory<Usuario, String>("username"));
        fxColumnName.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nombre"));
        fxColumnLevel.setCellValueFactory(new PropertyValueFactory<Usuario, Acceso>("acceso"));
        fxColumnIsActive.setCellValueFactory(f -> f.getValue().activeProperty());
        fxColumnIsActive.setCellFactory(tc -> new CheckBoxTableCell<>());
        fxTable.setItems(listedObjects);

        addContent();
    }


    @Override
    protected ActivableDao<Usuario> getDataOrigin() {
        return DataStore.getUsuarios();
    }

    @Override
    protected Pane getEditorPane() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editor/UserEditorPane.fxml"));
        Pane pane = loader.load();
        control = loader.getController();
        return pane;
    }

    @Override
    protected EditorControl<Usuario> getEditorControl() {
        return control;
    }
}
