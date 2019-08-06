package tiendaclub.data;

import tiendaclub.model.models.*;

public class DataStore {

    private static Caja caja;
    private static Sede sede;
    private static Usuario user;

    private static GenericDao<Acceso> accesos = new GenericDao<>(Acceso.TABLE_NAME);
    private static GenericDao<Sede> sedes = new GenericDao<>(Sede.TABLE_NAME);
    private static GenericDao<Caja> cajas = new GenericDao<>(Caja.TABLE_NAME);
    private static GenericDao<Categoria> categorias = new GenericDao<>(Categoria.TABLE_NAME);
    private static GenericDao<CierreZ> cierreZs = new GenericDao<>(CierreZ.TABLE_NAME);
    private static GenericDao<Compra> compras = new GenericDao<>(Compra.TABLE_NAME);
    private static GenericDao<Comprado> comprados = new GenericDao<>(Comprado.TABLE_NAME);
    private static GenericDao<Producto> productos = new GenericDao<>(Producto.TABLE_NAME);
    private static GenericDao<Proveedor> proveedores = new GenericDao<>(Proveedor.TABLE_NAME);
    private static GenericDao<Socio> socios = new GenericDao<>(Socio.TABLE_NAME);
    private static GenericDao<Transferencia> transferencias = new GenericDao<>(Transferencia.TABLE_NAME);
    private static GenericDao<Usuario> usuarios = new GenericDao<>(Usuario.TABLE_NAME);
    private static GenericDao<Vendido> vendidos = new GenericDao<>(Vendido.TABLE_NAME);
    private static GenericDao<Venta> ventas = new GenericDao<>(Venta.TABLE_NAME);

    public static GenericDao<Acceso> getAccesos() {
        return accesos;
    }

    public static GenericDao<Sede> getSedes() {
        return sedes;
    }

    public static GenericDao<Caja> getCajas() {
        return cajas;
    }

    public static GenericDao<Categoria> getCategorias() {
        return categorias;
    }

    public static GenericDao<CierreZ> getCierreZs() {
        return cierreZs;
    }

    public static GenericDao<Compra> getCompras() {
        return compras;
    }

    public static GenericDao<Comprado> getComprados() {
        return comprados;
    }

    public static GenericDao<Producto> getProductos() {
        return productos;
    }

    public static GenericDao<Proveedor> getProveedores() {
        return proveedores;
    }

    public static GenericDao<Socio> getSocios() {
        return socios;
    }

    public static GenericDao<Transferencia> getTransferencias() {
        return transferencias;
    }

    public static GenericDao<Usuario> getUsuarios() {
        return usuarios;
    }

    public static GenericDao<Vendido> getVendidos() {
        return vendidos;
    }

    public static GenericDao<Venta> getVentas() {
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
