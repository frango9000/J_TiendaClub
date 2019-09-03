package app;

import app.control.PropsLoader;
import app.data.DataStore;
import app.data.casteldao.SessionDB;
import app.model.Caja;
import app.model.Categoria;
import app.model.Producto;
import app.model.Proveedor;
import app.model.Sede;
import app.model.Socio;
import java.time.LocalDateTime;
import javafx.application.Application;
import javafx.stage.Stage;

public class TestFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        PropsLoader.loadProps();
        DataStore.firstQuery();
        DataStore.getVentas().getIndexCaja().getValues().forEach(System.out::println);


    }

    private void createMockData(boolean clean) {
        SessionDB.getSessionDB().setAutoclose(false);

        if (clean) {
            DataStore.getProductos().getDao().deleteSome(DataStore.getProductos().getAllCache());
            DataStore.getProveedores().getDao().deleteSome(DataStore.getProveedores().getAllCache());
            DataStore.getSocios().getDao().deleteSome(DataStore.getSocios().getAllCache());
            DataStore.getCategorias().getDao().deleteSome(DataStore.getCategorias().getAllCache());
            DataStore.getCajas().getDao().deleteSome(DataStore.getCajas().getAllCache());
            DataStore.getSedes().getDao().deleteSome(DataStore.getSedes().getAllCache());
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
        SessionDB.getSessionDB().setAutoclose(true);
    }
}
