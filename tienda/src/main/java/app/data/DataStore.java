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
import app.model.Acceso;
import app.model.Categoria;
import app.model.Proveedor;
import app.model.Sede;
import casteldao.SessionDB;
import casteldao.dao.DataSourceIdActive;
import casteldao.dao.DataSourceIdImpl;

public class DataStore {

    private static DataSourceIdImpl<Acceso> accesos = new DataSourceIdImpl<>(Acceso.TABLE_NAME, Acceso.class);
    private static DataSourceIdActive<Sede> sedes = new DataSourceIdActive<>(Sede.TABLE_NAME, Sede.class);
    private static CajaDao cajas = new CajaDao();
    private static DataSourceIdActive<Categoria> categorias = new DataSourceIdActive<>(Categoria.TABLE_NAME, Categoria.class);
    private static CierreZDao cierreZs = new CierreZDao();
    private static CompraDao compras = new CompraDao();
    private static CompradoDao comprados = new CompradoDao();
    private static ProductoDao productos = new ProductoDao();
    private static DataSourceIdActive<Proveedor> proveedores = new DataSourceIdActive<>(Proveedor.TABLE_NAME, Proveedor.class);
    private static SocioDao socios = new SocioDao();
    private static TransferenciaDao transferencias = new TransferenciaDao();
    private static UsuarioDao usuarios = new UsuarioDao();
    private static VendidoDao vendidos = new VendidoDao();
    private static VentaDao ventas = new VentaDao();

    public static DataSourceIdImpl<Acceso> getAccesos() {
        return accesos;
    }

    public static DataSourceIdActive<Sede> getSedes() {
        return sedes;
    }

    public static CajaDao getCajas() {
        return cajas;
    }

    public static DataSourceIdActive<Categoria> getCategorias() {
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

    public static DataSourceIdActive<Proveedor> getProveedores() {
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
        SessionDB.getSessionDB().setAutoclose(false);
        getAccesos().getDao().queryAll();
        getUsuarios().getAll();
        getProveedores().getAll();
        getSocios().getAll();
        getSedes().getAll();
        getCajas().getAll();
        getCategorias().getAll();
        getProductos().getAll();
        getVentas().getAll();
        getVendidos().getAll();
        SessionDB.getSessionDB().setAutoclose(true);
    }

}
