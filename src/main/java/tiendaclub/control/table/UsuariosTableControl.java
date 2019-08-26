package tiendaclub.control.table;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import tiendaclub.control.editor.EditorControl;
import tiendaclub.control.editor.UsuarioEditorControl;
import tiendaclub.data.DataStore;
import tiendaclub.data.framework.dao.core.IndexIdActiveDao;
import tiendaclub.model.models.Acceso;
import tiendaclub.model.models.Usuario;

public class UsuariosTableControl extends ActiveTableControl<Usuario> {


    @Override
    public void initialize() {
        super.initialize();
        TableColumn<Usuario, String> fxColumnUser = new TableColumn<>("Usuario");
        TableColumn<Usuario, String> fxColumnName = new TableColumn<>("Nombre");
        TableColumn<Usuario, Acceso> fxColumnLevel = new TableColumn<>("Nivel");
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
    protected EditorControl<Usuario> getEditorControl() {
        return UsuarioEditorControl.getPane();
    }
}