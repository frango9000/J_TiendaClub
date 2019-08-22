package tiendaclub.control.table;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import tiendaclub.data.DataStore;
import tiendaclub.data.framework.dao.core.IndexIdActiveDao;
import tiendaclub.model.models.Acceso;
import tiendaclub.model.models.Usuario;

public class UsuariosTableControl extends ActiveTableControl<Usuario> {

    private TableColumn<Usuario, String> fxColumnUser;
    private TableColumn<Usuario, String> fxColumnName;
    private TableColumn<Usuario, Acceso> fxColumnLevel;


    @Override
    public void initialize() {
        super.initialize();
        fxColumnUser = new TableColumn<>("Usuario");
        fxColumnName = new TableColumn<>("Nombre");
        fxColumnLevel = new TableColumn<>("Nivel");
        fxTable.getColumns().add(fxColumnUser);
        fxTable.getColumns().add(fxColumnName);
        fxTable.getColumns().add(fxColumnLevel);
        fxColumnUser.setCellValueFactory(new PropertyValueFactory<Usuario, String>("username"));
        fxColumnName.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nombre"));
        fxColumnLevel.setCellValueFactory(new PropertyValueFactory<Usuario, Acceso>("acceso"));

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
