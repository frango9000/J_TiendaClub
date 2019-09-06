package app.control.table;

import app.data.DataStore;
import app.model.Acceso;
import app.model.Usuario;
import casteldao.datasource.DataSourceIdActive;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

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
    protected String fxmlLocation() {
        return "/fxml/editor/UsuarioEditorGridPane.fxml";
    }


    @Override
    protected DataSourceIdActive<Usuario> getDataOrigin() {
        return DataStore.getSessionStore().getUsuarios();
    }

}
