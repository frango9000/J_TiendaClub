package tiendaclub.data;

import tiendaclub.data.dao.CajaDao;
import tiendaclub.data.dao.CierreZDao;
import tiendaclub.data.dao.CompraDao;
import tiendaclub.data.dao.CompradoDao;
import tiendaclub.data.dao.ProductoDao;
import tiendaclub.data.dao.SocioDao;
import tiendaclub.data.dao.TransferenciaDao;
import tiendaclub.data.dao.UsuarioDao;
import tiendaclub.data.dao.VendidoDao;
import tiendaclub.data.dao.VentaDao;
import tiendaclub.data.dao.core.IndexIdActiveDao;
import tiendaclub.data.dao.core.IndexIdDao;
import tiendaclub.data.framework.SessionDB;
import tiendaclub.model.models.Acceso;
import tiendaclub.model.models.Categoria;
import tiendaclub.model.models.Proveedor;
import tiendaclub.model.models.Sede;

public class DataStore {

    private static IndexIdDao<Acceso> accesos = new IndexIdDao<>(Acceso.TABLE_NAME, Acceso.class);
    private static IndexIdActiveDao<Sede> sedes = new IndexIdActiveDao<>(Sede.TABLE_NAME, Sede.class);
    private static CajaDao cajas = new CajaDao();
    private static IndexIdActiveDao<Categoria> categorias = new IndexIdActiveDao<>(Categoria.TABLE_NAME, Categoria.class);
    private static CierreZDao cierreZs = new CierreZDao();
    private static CompraDao compras = new CompraDao();
    private static CompradoDao comprados = new CompradoDao();
    private static ProductoDao productos = new ProductoDao();
    private static IndexIdActiveDao<Proveedor> proveedores = new IndexIdActiveDao<>(Proveedor.TABLE_NAME, Proveedor.class);
    private static SocioDao socios = new SocioDao();
    private static TransferenciaDao transferencias = new TransferenciaDao();
    private static UsuarioDao usuarios = new UsuarioDao();
    private static VendidoDao vendidos = new VendidoDao();
    private static VentaDao ventas = new VentaDao();

    public static IndexIdDao<Acceso> getAccesos() {
        return accesos;
    }

    public static IndexIdActiveDao<Sede> getSedes() {
        return sedes;
    }

    public static CajaDao getCajas() {
        return cajas;
    }

    public static IndexIdActiveDao<Categoria> getCategorias() {
        return categorias;
    }

    public static CierreZDao getCierreZs() {
        return cierreZs;
    }

    public static CompraDao getCompras() {
        return compras;
    }

    public static CompradoDao getComprados() {
        return comprados;
    }

    public static ProductoDao getProductos() {
        return productos;
    }

    public static IndexIdActiveDao<Proveedor> getProveedores() {
        return proveedores;
    }

    public static SocioDao getSocios() {
        return socios;
    }

    public static TransferenciaDao getTransferencias() {
        return transferencias;
    }

    public static UsuarioDao getUsuarios() {
        return usuarios;
    }

    public static VendidoDao getVendidos() {
        return vendidos;
    }

    public static VentaDao getVentas() {
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
}
