package app.control.table;

import app.control.MainPaneControl;
import app.data.DataStore;
import app.model.Sede;
import casteldao.datasource.DataSourceIdActive;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class SedesTableControl extends ActiveTableControl<Sede> {


    private MenuItem menuItemVerCompras = new MenuItem("Ver Compras");
    private MenuItem menuItemVerCajas = new MenuItem("Ver Cajas");

    {
        TableColumn<Sede, String> fxColumnSede = new TableColumn<>("Sede");
        TableColumn<Sede, String> fxColumnTelefono = new TableColumn<>("Telefono");
        TableColumn<Sede, String> fxColumnDireccion = new TableColumn<>("Direccion");
        fxTable.getColumns().add(fxColumnSede);
        fxTable.getColumns().add(fxColumnTelefono);
        fxTable.getColumns().add(fxColumnDireccion);
        fxColumnSede.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        fxColumnTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        fxColumnDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        fxTable.setItems(listedObjects);
        fxBtnMenu.getItems().addAll(menuItemVerCompras, menuItemVerCajas);
        menuItemVerCompras.setOnAction(event -> menuItemVerComprasAction(event));
        menuItemVerCajas.setOnAction(event -> menuItemVerCajasAction(event));

    }

    public SedesTableControl() {
        addContent();
    }

    private void menuItemVerCajasAction(ActionEvent event) {
        Sede selected = fxTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            CajasTableControl control = new CajasTableControl(selected);
            MainPaneControl.setCenter(control);
        }
    }

    private void menuItemVerComprasAction(ActionEvent event) {
        Sede selected = fxTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            ComprasTableControl control = new ComprasTableControl(selected);
            MainPaneControl.setCenter(control);
        }
    }

    @Override
    protected String fxmlLocation() {
        return "/fxml/editor/SedeEditorGridPane.fxml";
    }

    @Override
    protected DataSourceIdActive<Sede> getDataOrigin() {
        return DataStore.getSessionStore().getSedes();
    }
}
