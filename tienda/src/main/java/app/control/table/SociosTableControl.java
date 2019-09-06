package app.control.table;

import app.data.DataStore;
import app.model.Socio;
import casteldao.datasource.DataSourceIdActive;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class SociosTableControl extends ActiveTableControl<Socio> {


    @Override
    public void initialize() {
        super.initialize();
        TableColumn<Socio, String> fxColumnNif = new TableColumn<>("DNI");
        fxTable.getColumns().add(fxColumnNif);
        fxColumnNif.setCellValueFactory(new PropertyValueFactory<Socio, String>("dni"));

        TableColumn<Socio, String> fxColumnNombre = new TableColumn<>("Nombre");
        fxTable.getColumns().add(fxColumnNombre);
        fxColumnNombre.setCellValueFactory(new PropertyValueFactory<Socio, String>("nombre"));

        TableColumn<Socio, String> fxColumnTelefono = new TableColumn<>("Telefono");
        fxTable.getColumns().add(fxColumnTelefono);
        fxColumnTelefono.setCellValueFactory(new PropertyValueFactory<Socio, String>("telefono"));

        TableColumn<Socio, String> fxColumnEmail = new TableColumn<>("Email");
        fxTable.getColumns().add(fxColumnEmail);
        fxColumnEmail.setCellValueFactory(new PropertyValueFactory<Socio, String>("email"));

        fxTable.setItems(listedObjects);
        addContent();
    }

    @Override
    protected String fxmlLocation() {
        return "/fxml/editor/SocioEditorGridPane.fxml";
    }


    @Override
    protected DataSourceIdActive<Socio> getDataOrigin() {
        return DataStore.getSessionStore().getSocios();
    }
}
