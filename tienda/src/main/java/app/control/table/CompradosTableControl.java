package app.control.table;

import app.data.DataStore;
import app.data.appdao.CompradoDao;
import app.model.Compra;
import app.model.Comprado;
import app.model.Producto;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class CompradosTableControl extends TableControl<Comprado> {


    {
        TableColumn<Comprado, Compra> fxColumnCompra = new TableColumn<>("Compra");
        fxColumnCompra.setCellValueFactory(new PropertyValueFactory<>("compra"));
        fxTable.getColumns().add(fxColumnCompra);

        TableColumn<Comprado, Producto> fxColumnProducto = new TableColumn<>("Producto");
        fxColumnProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));
        fxTable.getColumns().add(fxColumnProducto);

        TableColumn<Comprado, Integer> fxColumnCantidad = new TableColumn<>("Cantidad");
        fxColumnCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        fxTable.getColumns().add(fxColumnCantidad);

        TableColumn<Comprado, Integer> fxColumnPrecioUnidad = new TableColumn<>("Precio Unidad");
        fxColumnPrecioUnidad.setCellValueFactory(new PropertyValueFactory<>("precioUnidad"));
        fxTable.getColumns().add(fxColumnPrecioUnidad);

        fxTable.setItems(listedObjects);
    }

    public CompradosTableControl() {
        addContent();
    }

    public CompradosTableControl(Compra compra) {
        listedObjects.addAll(compra.getComprados());
    }

    @Override
    protected String fxmlLocation() {
        return "/fxml/editor/CompradoEditorGridPane.fxml";
    }


    @Override
    protected CompradoDao getDataOrigin() {
        return DataStore.getSessionStore().getComprados();
    }
}
