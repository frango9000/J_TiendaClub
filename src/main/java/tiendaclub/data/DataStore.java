package tiendaclub.data;

import tiendaclub.data.framework.dao.IndexIdActiveDao;
import tiendaclub.data.framework.dao.IndexIdDao;
import tiendaclub.model.models.Acceso;
import tiendaclub.model.models.Caja;
import tiendaclub.model.models.Categoria;
import tiendaclub.model.models.CierreZ;
import tiendaclub.model.models.Compra;
import tiendaclub.model.models.Comprado;
import tiendaclub.model.models.Producto;
import tiendaclub.model.models.Proveedor;
import tiendaclub.model.models.Sede;
import tiendaclub.model.models.Socio;
import tiendaclub.model.models.Transferencia;
import tiendaclub.model.models.Usuario;
import tiendaclub.model.models.Vendido;
import tiendaclub.model.models.Venta;
import tiendaclub.model.models.abstracts.Persistible;

public class DataStore {

    private static IndexIdDao<Acceso> accesos = new IndexIdDao<>(Acceso.TABLE_NAME);
    private static IndexIdActiveDao<Sede> sedes = new IndexIdActiveDao<>(Sede.TABLE_NAME);
    private static IndexIdActiveDao<Caja> cajas = new IndexIdActiveDao<>(Caja.TABLE_NAME);
    private static IndexIdActiveDao<Categoria> categorias = new IndexIdActiveDao<>(Categoria.TABLE_NAME);
    private static IndexIdDao<CierreZ> cierreZs = new IndexIdDao<>(CierreZ.TABLE_NAME);
    private static IndexIdDao<Compra> compras = new IndexIdDao<>(Compra.TABLE_NAME);
    private static IndexIdDao<Comprado> comprados = new IndexIdDao<>(Comprado.TABLE_NAME);
    private static IndexIdActiveDao<Producto> productos = new IndexIdActiveDao<>(Producto.TABLE_NAME);
    private static IndexIdActiveDao<Proveedor> proveedores = new IndexIdActiveDao<>(Proveedor.TABLE_NAME);
    private static IndexIdActiveDao<Socio> socios = new IndexIdActiveDao<>(Socio.TABLE_NAME);
    private static IndexIdDao<Transferencia> transferencias = new IndexIdDao<>(Transferencia.TABLE_NAME);
    private static IndexIdActiveDao<Usuario> usuarios = new IndexIdActiveDao<>(Usuario.TABLE_NAME);
    private static IndexIdDao<Vendido> vendidos = new IndexIdDao<>(Vendido.TABLE_NAME);
    private static IndexIdDao<Venta> ventas = new IndexIdDao<>(Venta.TABLE_NAME);

    public static IndexIdDao<Acceso> getAccesos() {
        return accesos;
    }

    public static IndexIdActiveDao<Sede> getSedes() {
        return sedes;
    }

    public static IndexIdActiveDao<Caja> getCajas() {
        return cajas;
    }

    public static IndexIdActiveDao<Categoria> getCategorias() {
        return categorias;
    }

    public static IndexIdDao<CierreZ> getCierreZs() {
        return cierreZs;
    }

    public static IndexIdDao<Compra> getCompras() {
        return compras;
    }

    public static IndexIdDao<Comprado> getComprados() {
        return comprados;
    }

    public static IndexIdActiveDao<Producto> getProductos() {
        return productos;
    }

    public static IndexIdActiveDao<Proveedor> getProveedores() {
        return proveedores;
    }

    public static IndexIdActiveDao<Socio> getSocios() {
        return socios;
    }

    public static IndexIdDao<Transferencia> getTransferencias() {
        return transferencias;
    }

    public static IndexIdActiveDao<Usuario> getUsuarios() {
        return usuarios;
    }

    public static IndexIdDao<Vendido> getVendidos() {
        return vendidos;
    }

    public static IndexIdDao<Venta> getVentas() {
        return ventas;
    }

    public static void firstQuery() {
        SessionDB.setAutoclose(false);
        DataStore.getAccesos().getDataSource().queryAll();
        DataStore.getUsuarios().getDataSource().queryAll();
        DataStore.getProveedores().getDataSource().queryAll();
        DataStore.getSocios().getDataSource().queryAll();
        DataStore.getSedes().getDataSource().queryAll();
        DataStore.getCajas().getDataSource().queryAll();
        DataStore.getCategorias().getDataSource().queryAll();
        DataStore.getProductos().getDataSource().queryAll();
        SessionDB.setAutoclose(true);
    }

    public static <T extends Persistible> IndexIdDao getDataStore(T t) {
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
