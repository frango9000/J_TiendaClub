package app.control.table;

import app.data.DataStore;
import app.data.appdao.VendidoDao;
import app.model.Producto;
import app.model.Vendido;
import app.model.Venta;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class VendidosTableControl extends TableControl<Vendido> {


    {
        TableColumn<Vendido, Venta> fxColumnVenta = new TableColumn<>("Venta");
        fxColumnVenta.setCellValueFactory(new PropertyValueFactory<>("venta"));
        fxTable.getColumns().add(fxColumnVenta);

        TableColumn<Vendido, Producto> fxColumnProducto = new TableColumn<>("Producto");
        fxColumnProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));
        fxTable.getColumns().add(fxColumnProducto);

        TableColumn<Vendido, Integer> fxColumnCantidad = new TableColumn<>("Cantidad");
        fxColumnCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        fxTable.getColumns().add(fxColumnCantidad);

        TableColumn<Vendido, Integer> fxColumnPrecioUnidad = new TableColumn<>("Precio Unidad");
        fxColumnPrecioUnidad.setCellValueFactory(new PropertyValueFactory<>("precioUnidad"));
        fxTable.getColumns().add(fxColumnPrecioUnidad);

        fxTable.setItems(listedObjects);
    }

    public VendidosTableControl() {
        addContent();
    }

    public VendidosTableControl(Venta venta) {
        listedObjects.addAll(venta.getVendidos());
    }

    @Override
    protected String fxmlLocation() {
        return "/fxml/editor/VendidoEditorGridPane.fxml";
    }


    @Override
    protected VendidoDao getDataOrigin() {
        return DataStore.getSessionStore().getVendidos();
    }
}
