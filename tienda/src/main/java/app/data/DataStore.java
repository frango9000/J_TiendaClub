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
import app.model.Caja;
import app.model.Categoria;
import app.model.Proveedor;
import app.model.Sede;
import app.model.Usuario;
import casteldao.datasource.DataSourceIdActive;
import casteldao.datasource.DataSourceIdImpl;

public class DataStore {

    //Synchronized Singleton
    private static DataStore instance;
    //DB Session connection
    private SessionDB sessionDB = SessionDB.getSession();
    //Active User Session Details
    private Caja caja;
    private Sede sede;
    private Usuario usuario;
    //Object DataStore
    private DataSourceIdImpl<Acceso> accesos = new DataSourceIdImpl<>(getSessionDB(), Acceso.TABLE_NAME, Acceso.class);
    private DataSourceIdActive<Sede> sedes = new DataSourceIdActive<>(getSessionDB(), Sede.TABLE_NAME, Sede.class);
    private CajaDao cajas = new CajaDao();
    private DataSourceIdActive<Categoria> categorias = new DataSourceIdActive<>(getSessionDB(), Categoria.TABLE_NAME, Categoria.class);
    private CierreZDao cierreZs = new CierreZDao();
    private CompraDao compras = new CompraDao();
    private CompradoDao comprados = new CompradoDao();
    private ProductoDao productos = new ProductoDao();
    private DataSourceIdActive<Proveedor> proveedores = new DataSourceIdActive<>(getSessionDB(), Proveedor.TABLE_NAME, Proveedor.class);
    private SocioDao socios = new SocioDao();
    private TransferenciaDao transferencias = new TransferenciaDao();
    private UsuarioDao usuarios = new UsuarioDao();
    private VendidoDao vendidos = new VendidoDao();
    private VentaDao ventas = new VentaDao();

    private DataStore() {
    }

    public static DataStore getSessionStore() {
        if (instance == null) {
            synchronized (DataStore.class) {
                if (instance == null) {
                    instance = new DataStore();
                }
            }
        }
        return instance;
    }

    private SessionDB getSessionDB() {
        return sessionDB;
    }

    public void setSessionDB(SessionDB sessionDB) {
        this.sessionDB = sessionDB;
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public DataSourceIdImpl<Acceso> getAccesos() {
        return accesos;
    }

    public DataSourceIdActive<Sede> getSedes() {
        return sedes;
    }

    public CajaDao getCajas() {
        return cajas;
    }

    public DataSourceIdActive<Categoria> getCategorias() {
        return categorias;
    }

    public CierreZDao getCierreZs() {
        return cierreZs;
    }

    public CompraDao getCompras() {
        return compras;
    }

    public CompradoDao getComprados() {
        return comprados;
    }

    public ProductoDao getProductos() {
        return productos;
    }

    public DataSourceIdActive<Proveedor> getProveedores() {
        return proveedores;
    }

    public SocioDao getSocios() {
        return socios;
    }

    public TransferenciaDao getTransferencias() {
        return transferencias;
    }

    public UsuarioDao getUsuarios() {
        return usuarios;
    }

    public VendidoDao getVendidos() {
        return vendidos;
    }

    public VentaDao getVentas() {
        return ventas;
    }

    public void firstQuery() {
        getSessionDB().setAutoclose(false);
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
        getSessionDB().setAutoclose(true);
    }


}
