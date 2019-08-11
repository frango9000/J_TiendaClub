package tiendaclub.data;

import tiendaclub.data.framework.dao.ActivableDao;
import tiendaclub.data.framework.dao.IdentifiableDao;
import tiendaclub.model.models.*;

public class DataStore {

    private static Caja caja;
    private static Sede sede;
    private static Usuario user;

    private static IdentifiableDao<Acceso> accesos = new IdentifiableDao<>(Acceso.TABLE_NAME);
    private static IdentifiableDao<Sede> sedes = new IdentifiableDao<>(Sede.TABLE_NAME);
    private static IdentifiableDao<Caja> cajas = new IdentifiableDao<>(Caja.TABLE_NAME);
    private static IdentifiableDao<Categoria> categorias = new IdentifiableDao<>(Categoria.TABLE_NAME);
    private static IdentifiableDao<CierreZ> cierreZs = new IdentifiableDao<>(CierreZ.TABLE_NAME);
    private static IdentifiableDao<Compra> compras = new IdentifiableDao<>(Compra.TABLE_NAME);
    private static IdentifiableDao<Comprado> comprados = new IdentifiableDao<>(Comprado.TABLE_NAME);
    private static IdentifiableDao<Producto> productos = new IdentifiableDao<>(Producto.TABLE_NAME);
    private static IdentifiableDao<Proveedor> proveedores = new IdentifiableDao<>(Proveedor.TABLE_NAME);
    private static IdentifiableDao<Socio> socios = new IdentifiableDao<>(Socio.TABLE_NAME);
    private static IdentifiableDao<Transferencia> transferencias = new IdentifiableDao<>(Transferencia.TABLE_NAME);
    private static ActivableDao<Usuario> usuarios = new ActivableDao<>(Usuario.TABLE_NAME);
    private static IdentifiableDao<Vendido> vendidos = new IdentifiableDao<>(Vendido.TABLE_NAME);
    private static IdentifiableDao<Venta> ventas = new IdentifiableDao<>(Venta.TABLE_NAME);

    public static IdentifiableDao<Acceso> getAccesos() {
        return accesos;
    }

    public static IdentifiableDao<Sede> getSedes() {
        return sedes;
    }

    public static IdentifiableDao<Caja> getCajas() {
        return cajas;
    }

    public static IdentifiableDao<Categoria> getCategorias() {
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

    public static IdentifiableDao<Producto> getProductos() {
        return productos;
    }

    public static IdentifiableDao<Proveedor> getProveedores() {
        return proveedores;
    }

    public static IdentifiableDao<Socio> getSocios() {
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

    public static Caja getCaja() {
        return caja;
    }

    public static void setCaja(Caja caja) {
        DataStore.caja = caja;
    }

    public static Sede getSede() {
        return sede;
    }

    public static void setSede(Sede sede) {
        DataStore.sede = sede;
    }

    public static Usuario getUser() {
        return user;
    }

    public static void setUser(Usuario user) {
        DataStore.user = user;
    }
}
