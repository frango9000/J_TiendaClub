package tiendaclub.data;

import tiendaclub.model.models.*;

public class DataStore {

    private static Caja caja;
    private static Sede sede;
    private static Usuario user;

    private static PersistableDao<Acceso> accesos = new PersistableDao<>(Acceso.TABLE_NAME);
    private static PersistableDao<Sede> sedes = new PersistableDao<>(Sede.TABLE_NAME);
    private static PersistableDao<Caja> cajas = new PersistableDao<>(Caja.TABLE_NAME);
    private static PersistableDao<Categoria> categorias = new PersistableDao<>(Categoria.TABLE_NAME);
    private static PersistableDao<CierreZ> cierreZs = new PersistableDao<>(CierreZ.TABLE_NAME);
    private static PersistableDao<Compra> compras = new PersistableDao<>(Compra.TABLE_NAME);
    private static PersistableDao<Comprado> comprados = new PersistableDao<>(Comprado.TABLE_NAME);
    private static PersistableDao<Producto> productos = new PersistableDao<>(Producto.TABLE_NAME);
    private static PersistableDao<Proveedor> proveedores = new PersistableDao<>(Proveedor.TABLE_NAME);
    private static PersistableDao<Socio> socios = new PersistableDao<>(Socio.TABLE_NAME);
    private static PersistableDao<Transferencia> transferencias = new PersistableDao<>(Transferencia.TABLE_NAME);
    private static ActivableDao<Usuario> usuarios = new ActivableDao<>(Usuario.TABLE_NAME);
    private static PersistableDao<Vendido> vendidos = new PersistableDao<>(Vendido.TABLE_NAME);
    private static PersistableDao<Venta> ventas = new PersistableDao<>(Venta.TABLE_NAME);

    public static PersistableDao<Acceso> getAccesos() {
        return accesos;
    }

    public static PersistableDao<Sede> getSedes() {
        return sedes;
    }

    public static PersistableDao<Caja> getCajas() {
        return cajas;
    }

    public static PersistableDao<Categoria> getCategorias() {
        return categorias;
    }

    public static PersistableDao<CierreZ> getCierreZs() {
        return cierreZs;
    }

    public static PersistableDao<Compra> getCompras() {
        return compras;
    }

    public static PersistableDao<Comprado> getComprados() {
        return comprados;
    }

    public static PersistableDao<Producto> getProductos() {
        return productos;
    }

    public static PersistableDao<Proveedor> getProveedores() {
        return proveedores;
    }

    public static PersistableDao<Socio> getSocios() {
        return socios;
    }

    public static PersistableDao<Transferencia> getTransferencias() {
        return transferencias;
    }

    public static ActivableDao<Usuario> getUsuarios() {
        return usuarios;
    }

    public static PersistableDao<Vendido> getVendidos() {
        return vendidos;
    }

    public static PersistableDao<Venta> getVentas() {
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
