package tiendaclub.control.table;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import tiendaclub.data.DataStore;
import tiendaclub.data.dao.core.IndexIdActiveDao;
import tiendaclub.model.models.Categoria;
import tiendaclub.model.models.Producto;

public class ProductosTableControl extends ActiveTableControl<Producto> {


    @Override
    public void initialize() {
        super.initialize();
        TableColumn<Producto, String> fxColumnName = new TableColumn<>("Nombre");
        fxColumnName.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombre"));
        fxTable.getColumns().add(fxColumnName);

        TableColumn<Producto, Categoria> fxColumnCategoria = new TableColumn<>("Categoria");
        fxColumnCategoria.setCellValueFactory(new PropertyValueFactory<Producto, Categoria>("categoria"));
        fxTable.getColumns().add(fxColumnCategoria);

        TableColumn<Producto, Integer> fxColumnPrecio = new TableColumn<>("Precio");
        fxColumnPrecio.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("precioVenta"));
        fxTable.getColumns().add(fxColumnPrecio);

        fxTable.setItems(listedObjects);
        addContent();
    }

    @Override
    protected String fxmlLocation() {
        return "/fxml/editor/ProductoEditorGridPane.fxml";
    }


    @Override
    protected IndexIdActiveDao<Producto> getDataOrigin() {
        return DataStore.getProductos();
    }


}
