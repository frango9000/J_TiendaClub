package app.control.table;

import app.control.MainPaneControl;
import app.data.DataStore;
import app.model.Acceso;
import app.model.Usuario;
import casteldao.datasource.DataSourceIdActive;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class UsuariosTableControl extends ActiveTableControl<Usuario> {

    private MenuItem menuItemVerZs = new MenuItem("Ver Zs");
    private MenuItem menuItemVerCompras = new MenuItem("Ver Compras");
    private MenuItem menuItemVerVentas = new MenuItem("Ver Ventas");

    {
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

        fxBtnMenu.getItems().addAll(menuItemVerZs, menuItemVerCompras, menuItemVerVentas);
        menuItemVerZs.setOnAction(event -> menuItemVerZsAction(event));
        menuItemVerCompras.setOnAction(event -> menuItemVerComprasAction(event));
        menuItemVerVentas.setOnAction(event -> menuItemVerVentasAction(event));
    }

    public UsuariosTableControl() {
        addContent();
    }

    public UsuariosTableControl(Acceso acceso) {
        listedObjects.addAll(acceso.getUsuarios());
    }


    private void menuItemVerVentasAction(ActionEvent event) {
        Usuario selected = fxTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            VentasTableControl control = new VentasTableControl(selected);
            MainPaneControl.setCenter(control);
        }
    }

    private void menuItemVerComprasAction(ActionEvent event) {
        Usuario selected = fxTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            ComprasTableControl control = new ComprasTableControl(selected);
            MainPaneControl.setCenter(control);
        }
    }

    private void menuItemVerZsAction(ActionEvent event) {
        Usuario selected = fxTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            CierreZTableControl control = new CierreZTableControl(selected);
            MainPaneControl.setCenter(control);
        }
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
