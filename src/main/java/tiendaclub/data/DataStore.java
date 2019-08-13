package tiendaclub.data;

import tiendaclub.data.framework.dao.ActivableDao;
import tiendaclub.data.framework.dao.IdentifiableDao;
import tiendaclub.model.models.*;
import tiendaclub.model.models.abstracts.Persistible;

public class DataStore {

    private static IdentifiableDao<Acceso> accesos = new IdentifiableDao<>(Acceso.TABLE_NAME);
    private static ActivableDao<Sede> sedes = new ActivableDao<>(Sede.TABLE_NAME);
    private static ActivableDao<Caja> cajas = new ActivableDao<>(Caja.TABLE_NAME);
    private static ActivableDao<Categoria> categorias = new ActivableDao<>(Categoria.TABLE_NAME);
    private static IdentifiableDao<CierreZ> cierreZs = new IdentifiableDao<>(CierreZ.TABLE_NAME);
    private static IdentifiableDao<Compra> compras = new IdentifiableDao<>(Compra.TABLE_NAME);
    private static IdentifiableDao<Comprado> comprados = new IdentifiableDao<>(Comprado.TABLE_NAME);
    private static ActivableDao<Producto> productos = new ActivableDao<>(Producto.TABLE_NAME);
    private static ActivableDao<Proveedor> proveedores = new ActivableDao<>(Proveedor.TABLE_NAME);
    private static ActivableDao<Socio> socios = new ActivableDao<>(Socio.TABLE_NAME);
    private static IdentifiableDao<Transferencia> transferencias = new IdentifiableDao<>(Transferencia.TABLE_NAME);
    private static ActivableDao<Usuario> usuarios = new ActivableDao<>(Usuario.TABLE_NAME);
    private static IdentifiableDao<Vendido> vendidos = new IdentifiableDao<>(Vendido.TABLE_NAME);
    private static IdentifiableDao<Venta> ventas = new IdentifiableDao<>(Venta.TABLE_NAME);

    public static IdentifiableDao<Acceso> getAccesos() {
        return accesos;
    }

    public static ActivableDao<Sede> getSedes() {
        return sedes;
    }

    public static ActivableDao<Caja> getCajas() {
        return cajas;
    }

    public static ActivableDao<Categoria> getCategorias() {
        return categorias;
    }

    public static IdentifiableDao<CierreZ> getCierreZs() {
        return cierreZs;
    }

    public static IdentifiableDao<Compra> getCompras() {
        return compras;
    }

    public static IdentifiableDao<Comprado> getComprados() {
        return comprados;
    }

    public static ActivableDao<Producto> getProductos() {
        return productos;
    }

    public static ActivableDao<Proveedor> getProveedores() {
        return proveedores;
    }

    public static ActivableDao<Socio> getSocios() {
        return socios;
    }

    public static IdentifiableDao<Transferencia> getTransferencias() {
        return transferencias;
    }

    public static ActivableDao<Usuario> getUsuarios() {
        return usuarios;
    }

    public static IdentifiableDao<Vendido> getVendidos() {
        return vendidos;
    }

    public static IdentifiableDao<Venta> getVentas() {
        return ventas;
    }

    public static void firstQuery() {
        SessionDB.setAutoclose(false);
        DataStore.getAccesos().queryAll();
        DataStore.getUsuarios().queryAll();
        DataStore.getProveedores().queryAll();
        DataStore.getSocios().queryAll();
        DataStore.getSedes().queryAll();
        DataStore.getCajas().queryAll();
        DataStore.getCategorias().queryAll();
        DataStore.getProductos().queryAll();
        SessionDB.setAutoclose(true);
    }

    public static <T extends Persistible> IdentifiableDao getDataStore(T t) {
        switch (t.getTableName()) {
            case Acceso.TABLE_NAME:
                return DataStore.getAccesos();
            case Sede.TABLE_NAME:
                return DataStore.getSedes();
            case Caja.TABLE_NAME:
                return DataStore.getCajas();
            case Categoria.TABLE_NAME:
                return DataStore.getCategorias();
            case CierreZ.TABLE_NAME:
                return DataStore.getCierreZs();
            case Compra.TABLE_NAME:
                return DataStore.getCompras();
            case Comprado.TABLE_NAME:
                return DataStore.getComprados();
            case Producto.TABLE_NAME:
                return DataStore.getProductos();
            case Proveedor.TABLE_NAME:
                return DataStore.getProveedores();
            case Socio.TABLE_NAME:
                return DataStore.getSocios();
            case Transferencia.TABLE_NAME:
                return DataStore.getTransferencias();
            case Usuario.TABLE_NAME:
                return DataStore.getUsuarios();
            case Vendido.TABLE_NAME:
                return DataStore.getVendidos();
            case Venta.TABLE_NAME:
                return DataStore.getVentas();
            default:
                throw new IllegalStateException("Unexpected value: " + t.getTableName());
        }
    }

}
