package tiendaclub.model;

import tiendaclub.model.models.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DataStore {

    public static Usuario user;

    public static Usuario getUser() {
        return user;
    }

    public static void setUser(Usuario user) {
        DataStore.user = user;
    }

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

    static <T extends IPersistible> T buildObject(ResultSet rs) throws SQLException {
        switch (rs.getMetaData().getTableName(1)) {
            case Acceso.TABLE_NAME:
                return (T) new Acceso(rs);
            case Sede.TABLE_NAME:
                return (T) new Sede(rs);
            case Caja.TABLE_NAME:
                return (T) new Caja(rs);
            case Categoria.TABLE_NAME:
                return (T) new Categoria(rs);
            case CierreZ.TABLE_NAME:
                return (T) new CierreZ(rs);
            case Compra.TABLE_NAME:
                return (T) new Compra(rs);
            case Comprado.TABLE_NAME:
                return (T) new Comprado(rs);
            case Producto.TABLE_NAME:
                return (T) new Producto(rs);
            case Proveedor.TABLE_NAME:
                return (T) new Proveedor(rs);
            case Socio.TABLE_NAME:
                return (T) new Socio(rs);
            case Transferencia.TABLE_NAME:
                return (T) new Transferencia(rs);
            case Usuario.TABLE_NAME:
                return (T) new Usuario(rs);
            case Vendido.TABLE_NAME:
                return (T) new Vendido(rs);
            case Venta.TABLE_NAME:
                return (T) new Venta(rs);
            default:
                throw new IllegalStateException("Unexpected value: " + rs.getMetaData().getTableName(1));
        }
    }


}
