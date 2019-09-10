package app.control.table;

import app.control.MainPaneControl;
import app.data.DataStore;
import app.model.Socio;
import casteldao.datasource.DataSourceIdActive;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class SociosTableControl extends ActiveTableControl<Socio> {

    private MenuItem menuItemVerVentas = new MenuItem("Ver Ventas");

    {
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

        fxBtnMenu.getItems().add(menuItemVerVentas);
        menuItemVerVentas.setOnAction(event -> menuItemVerVentasAction(event));
    }

    public SociosTableControl() {
        addContent();
    }


    private void menuItemVerVentasAction(ActionEvent event) {
        Socio selected = fxTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            VentasTableControl ventaControl = new VentasTableControl(selected);
            MainPaneControl.setCenter(ventaControl);
        }
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
