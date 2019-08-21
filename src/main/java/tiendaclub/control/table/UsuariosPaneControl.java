package tiendaclub.control.table;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import tiendaclub.data.DataStore;
import tiendaclub.data.framework.dao.core.IndexIdActiveDao;
import tiendaclub.model.models.Acceso;
import tiendaclub.model.models.Usuario;
import tiendaclub.view.FXMLStage;

public class UsuariosPaneControl extends ActiveTableControl<Usuario> {

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
    protected IndexIdActiveDao<Usuario> getDataOrigin() {
        return DataStore.getUsuarios();
    }

    @Override
    protected Pane getEditorPane() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editor/UserEditorPane.fxml"));
        Pane pane = loader.load();
        editorControl = loader.getController();
        return pane;
    }
}
