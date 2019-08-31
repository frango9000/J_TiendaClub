package tiendaclub;

import java.time.LocalDateTime;
import javafx.application.Application;
import javafx.stage.Stage;
import tiendaclub.control.PropsLoader;
import tiendaclub.data.DataStore;
import tiendaclub.data.casteldao.SessionDB;
import tiendaclub.model.Caja;
import tiendaclub.model.Categoria;
import tiendaclub.model.Producto;
import tiendaclub.model.Proveedor;
import tiendaclub.model.Sede;
import tiendaclub.model.Socio;

public class TestFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        PropsLoader.loadProps();
        DataStore.firstQuery();
        DataStore.getVentas().getIndexCaja().getValues().forEach(System.out::println);


    }

    private void createMockData(boolean clean) {
        SessionDB.setAutoclose(false);

        if (clean) {
            DataStore.getProductos().getDataSource().deleteSome(DataStore.getProductos().getAllCache());
            DataStore.getProveedores().getDataSource().deleteSome(DataStore.getProveedores().getAllCache());
            DataStore.getSocios().getDataSource().deleteSome(DataStore.getSocios().getAllCache());
            DataStore.getCategorias().getDataSource().deleteSome(DataStore.getCategorias().getAllCache());
            DataStore.getCajas().getDataSource().deleteSome(DataStore.getCajas().getAllCache());
            DataStore.getSedes().getDataSource().deleteSome(DataStore.getSedes().getAllCache());
        }
        for (int i = 0; i < 10; i++) {
            Categoria categoria = new Categoria("Cat " + i);
            categoria.insertIntoDB();
            Proveedor proveedor = new Proveedor("B000000" + i);
            proveedor.setNombre("Prov " + i);
            proveedor.insertIntoDB();
            for (int j = 0; j < 10; j++) {
                Producto producto = new Producto("Pro C" + i + "P" + j, categoria);
                producto.setPrecioVenta(i);
                producto.insertIntoDB();
                Socio socio = new Socio("0000" + i + "0" + j);
                socio.setNombre("Soc " + i);
                socio.setFechaIn(LocalDateTime.now());
                socio.insertIntoDB();
            }
        }

        for (int i = 0; i < 5; i++) {
            Sede sede = new Sede("Sede " + i);
            sede.insertIntoDB();
            for (int j = 0; j < 5; j++) {
                Caja caja = new Caja(sede, "Caja S" + i + "C" + j);
                caja.insertIntoDB();
            }
        }
        SessionDB.setAutoclose(true);
    }
}
