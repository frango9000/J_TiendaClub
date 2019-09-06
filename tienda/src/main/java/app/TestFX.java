package app;

import app.data.DataStore;
import app.data.SessionDB;
import app.misc.PropsLoader;
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
        DataStore sessionStore = DataStore.getSessionStore();
        sessionStore.firstQuery();
//        sessionStore.getVentas().getIndexCaja().getValues().forEach(System.out::println);
        reinitDB();
        createMockData(true);
        System.exit(0);

    }

    private void reinitDB() {
        SessionDB sessionDB = SessionDB.getSession();
        sessionDB.dropCatalog(sessionDB.getJdbcCatalog());
        sessionDB.createCatalog(sessionDB.getJdbcCatalog());
        sessionDB.createFullStructure();
    }

    private void createMockData(boolean clean) {
        DataStore sessionStore = DataStore.getSessionStore();
        SessionDB.getSession().setAutoclose(false);

        if (clean) {
            sessionStore.getProductos().getDao().deleteSome(sessionStore.getProductos().getAllCache());
            sessionStore.getProveedores().getDao().deleteSome(sessionStore.getProveedores().getAllCache());
            sessionStore.getSocios().getDao().deleteSome(sessionStore.getSocios().getAllCache());
            sessionStore.getCategorias().getDao().deleteSome(sessionStore.getCategorias().getAllCache());
            sessionStore.getCajas().getDao().deleteSome(sessionStore.getCajas().getAllCache());
            sessionStore.getSedes().getDao().deleteSome(sessionStore.getSedes().getAllCache());
        }
        for (int i = 0; i < 8; i++) {
            Categoria categoria = new Categoria("Cat " + i);
            categoria.insertIntoDB();
            Proveedor proveedor = new Proveedor("B000000" + i);
            proveedor.setNombre("Prov " + i);
            proveedor.insertIntoDB();
            for (int j = 0; j < 4; j++) {
                Producto producto = new Producto("Pro C" + i + "P" + j, categoria);
                producto.setPrecioVenta(i);
                producto.insertIntoDB();
                Socio socio = new Socio("0000" + i + "0" + j);
                socio.setNombre("Soc " + i + " " + j);
                socio.setFechaIn(LocalDateTime.now());
                socio.insertIntoDB();
            }
        }

        for (int i = 0; i < 3; i++) {
            Sede sede = new Sede("Sede " + i);
            sede.insertIntoDB();
            for (int j = 0; j < 2; j++) {
                Caja caja = new Caja(sede, "Caja S" + i + "C" + j);
                caja.insertIntoDB();
            }
        }
        SessionDB.getSession().setAutoclose(true);
    }
}
