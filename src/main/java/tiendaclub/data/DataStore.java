package tiendaclub.data;

import tiendaclub.data.framework.dao.IdBoolIndexDao;
import tiendaclub.data.framework.dao.IdIndexDao;
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

    private static IdIndexDao<Acceso> accesos = new IdIndexDao<>(Acceso.TABLE_NAME);
    private static IdBoolIndexDao<Sede> sedes = new IdBoolIndexDao<>(Sede.TABLE_NAME);
    private static IdBoolIndexDao<Caja> cajas = new IdBoolIndexDao<>(Caja.TABLE_NAME);
    private static IdBoolIndexDao<Categoria> categorias = new IdBoolIndexDao<>(Categoria.TABLE_NAME);
    private static IdIndexDao<CierreZ> cierreZs = new IdIndexDao<>(CierreZ.TABLE_NAME);
    private static IdIndexDao<Compra> compras = new IdIndexDao<>(Compra.TABLE_NAME);
    private static IdIndexDao<Comprado> comprados = new IdIndexDao<>(Comprado.TABLE_NAME);
    private static IdBoolIndexDao<Producto> productos = new IdBoolIndexDao<>(Producto.TABLE_NAME);
    private static IdBoolIndexDao<Proveedor> proveedores = new IdBoolIndexDao<>(Proveedor.TABLE_NAME);
    private static IdBoolIndexDao<Socio> socios = new IdBoolIndexDao<>(Socio.TABLE_NAME);
    private static IdIndexDao<Transferencia> transferencias = new IdIndexDao<>(Transferencia.TABLE_NAME);
    private static IdBoolIndexDao<Usuario> usuarios = new IdBoolIndexDao<>(Usuario.TABLE_NAME);
    private static IdIndexDao<Vendido> vendidos = new IdIndexDao<>(Vendido.TABLE_NAME);
    private static IdIndexDao<Venta> ventas = new IdIndexDao<>(Venta.TABLE_NAME);

    public static IdIndexDao<Acceso> getAccesos() {
        return accesos;
    }

    public static IdBoolIndexDao<Sede> getSedes() {
        return sedes;
    }

    public static IdBoolIndexDao<Caja> getCajas() {
        return cajas;
    }

    public static IdBoolIndexDao<Categoria> getCategorias() {
        return categorias;
    }

    public static IdIndexDao<CierreZ> getCierreZs() {
        return cierreZs;
    }

    public static IdIndexDao<Compra> getCompras() {
        return compras;
    }

    public static IdIndexDao<Comprado> getComprados() {
        return comprados;
    }

    public static IdBoolIndexDao<Producto> getProductos() {
        return productos;
    }

    public static IdBoolIndexDao<Proveedor> getProveedores() {
        return proveedores;
    }

    public static IdBoolIndexDao<Socio> getSocios() {
        return socios;
    }

    public static IdIndexDao<Transferencia> getTransferencias() {
        return transferencias;
    }

    public static IdBoolIndexDao<Usuario> getUsuarios() {
        return usuarios;
    }

    public static IdIndexDao<Vendido> getVendidos() {
        return vendidos;
    }

    public static IdIndexDao<Venta> getVentas() {
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

    public static <T extends Persistible> IdIndexDao getDataStore(T t) {
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
