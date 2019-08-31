package app.data;

import app.data.appdao.CajaDao;
import app.data.appdao.CierreZDao;
import app.data.appdao.CompraDao;
import app.data.appdao.CompradoDao;
import app.data.appdao.ProductoDao;
import app.data.appdao.SocioDao;
import app.data.appdao.TransferenciaDao;
import app.data.appdao.UsuarioDao;
import app.data.appdao.VendidoDao;
import app.data.appdao.VentaDao;
import app.data.casteldao.SessionDB;
import app.data.casteldao.daomodel.IndexIdActiveDao;
import app.data.casteldao.daomodel.IndexIdDao;
import app.model.Acceso;
import app.model.Categoria;
import app.model.Proveedor;
import app.model.Sede;

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
        getAccesos().getDataSource().queryAll();
        getUsuarios().getAll();
        getProveedores().getAll();
        getSocios().getAll();
        getSedes().getAll();
        getCajas().getAll();
        getCategorias().getAll();
        getProductos().getAll();
        getVentas().getAll();
        getVendidos().getAll();
        SessionDB.setAutoclose(true);
    }
}
