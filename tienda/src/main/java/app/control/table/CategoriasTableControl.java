package app.control.table;

import app.control.MainPaneControl;
import app.data.DataStore;
import app.model.Categoria;
import casteldao.datasource.DataSourceIdActive;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class CategoriasTableControl extends ActiveTableControl<Categoria> {


    private MenuItem menuItemVerProductos = new MenuItem("Ver Productos");

    {
        TableColumn<Categoria, String> fxColumnName = new TableColumn<>("Nombre");
        fxColumnName.setCellValueFactory(new PropertyValueFactory<Categoria, String>("nombre"));
        fxTable.getColumns().add(fxColumnName);

        fxTable.setItems(listedObjects);

        fxBtnMenu.getItems().addAll(menuItemVerProductos);
        menuItemVerProductos.setOnAction(event -> menuItemVerProductosAction(event));

    }

    public CategoriasTableControl() {
        addContent();
    }

    private void menuItemVerProductosAction(ActionEvent event) {
        Categoria selected = fxTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            ProductosTableControl control = new ProductosTableControl(selected);
            MainPaneControl.setCenter(control);
        }
    }

    @Override
    protected String fxmlLocation() {
        return "/fxml/editor/CategoriaEditorGridPane.fxml";
    }


    @Override
    protected DataSourceIdActive<Categoria> getDataOrigin() {
        return DataStore.getSessionStore().getCategorias();
    }


}
