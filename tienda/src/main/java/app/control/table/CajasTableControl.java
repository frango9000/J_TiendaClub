package app.control.table;

import app.control.MainPaneControl;
import app.data.DataStore;
import app.model.Caja;
import app.model.Sede;
import casteldao.datasource.DataSourceIdActive;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class CajasTableControl extends ActiveTableControl<Caja> {


    private MenuItem menuItemVerVentas = new MenuItem("Ver Ventas");
    private MenuItem menuItemVerCierreZs = new MenuItem("Ver Zs");

    {
        TableColumn<Caja, Sede> fxColumnSede = new TableColumn<>("Sede");
        fxColumnSede.setCellValueFactory(new PropertyValueFactory<Caja, Sede>("sede"));
        fxTable.getColumns().add(fxColumnSede);

        TableColumn<Caja, String> fxColumnName = new TableColumn<>("Nombre");
        fxColumnName.setCellValueFactory(new PropertyValueFactory<Caja, String>("nombre"));
        fxTable.getColumns().add(fxColumnName);

        fxTable.setItems(listedObjects);
        fxBtnMenu.getItems().addAll(menuItemVerVentas, menuItemVerCierreZs);
        menuItemVerVentas.setOnAction(event -> menuItemVerVentasAction(event));
        menuItemVerCierreZs.setOnAction(event -> menuItemVerCierreZsAction(event));
    }

    public CajasTableControl() {
        addContent();
    }

    public CajasTableControl(Sede sede) {
        listedObjects.addAll(sede.getCajas());
    }

    private void menuItemVerCierreZsAction(ActionEvent event) {
        Caja selected = fxTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            CierreZTableControl ventaControl = new CierreZTableControl(selected);
            MainPaneControl.setCenter(ventaControl);
        }
    }

    private void menuItemVerVentasAction(ActionEvent event) {
        Caja selected = fxTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            VentasTableControl ventaControl = new VentasTableControl(selected);
            MainPaneControl.setCenter(ventaControl);
        }
    }
    @Override
    protected String fxmlLocation() {
        return "/fxml/editor/CajaEditorGridPane.fxml";
    }


    @Override
    protected DataSourceIdActive<Caja> getDataOrigin() {
        return DataStore.getSessionStore().getCajas();
    }


}
