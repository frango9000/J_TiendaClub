package tiendaclub.model.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import tiendaclub.data.DataStore;
import tiendaclub.model.models.abstracts.AbstractProducto;

public class Producto extends AbstractProducto {

    public static final String TABLE_NAME = "productos";
    private static final ArrayList<String> COL_NAMES = new ArrayList<>(
            Arrays.asList("nombre", "descripcion", "precio_venta", "iva", "idCategoria", "activo"));

    private Categoria categoria;

    //private HashMap<Integer, Comprado> comprados = new HashMap<>(); //No Use
    //private HashMap<Integer, Transferencia> transferencias = new HashMap<>(); //No Use


    public Producto(int id, String nombre, int precioVenta, int iva, int idCategoria) {
        super(id, nombre, precioVenta, iva, idCategoria);
        updateCategoria();
    }

    public Producto(String nombre, int precioVenta, int iva, int idCategoria) {
        super(nombre, precioVenta, iva, idCategoria);
        updateCategoria();
    }

    public Producto(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getString(2), rs.getInt(4), rs.getInt(5), rs.getInt(6));
        setDescripcion(rs.getString(3));
        setActivo(rs.getBoolean(7));
    }

    @Override
    public void buildStatement(PreparedStatement pst) throws SQLException {
        pst.setString(1, getNombre());
        pst.setString(2, getDescripcion());
        pst.setInt(3, getPrecioVenta());
        pst.setInt(4, getIva());
        pst.setInt(5, getIdCategoria());
        pst.setBoolean(6, isActivo());
    }

    @Override
    public void updateObject(ResultSet rs) throws SQLException {
        //setId(rs.getInt(1));
        setNombre(rs.getString(2));
        setDescripcion(rs.getString(3));
        setPrecioVenta(rs.getInt(4));
        setIva(rs.getInt(5));
        setIdCategoria(rs.getInt(6));
        setActivo(rs.getBoolean(7));
    }

    @Override
    public void setIdCategoria(int idCategoria) {
        super.setIdCategoria(idCategoria);
        updateCategoria();
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria2) {
        if (categoria != null) {
            categoria.getProductos().remove(getId());
        }
        this.categoria = categoria2;
        if (categoria != null) {
            categoria.getProductos().put(getId(), this);
        }
    }

    private void updateCategoria() {
        setCategoria(DataStore.getCategorias().getIdIndex().getCacheValue(getIdCategoria()));
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public ArrayList<String> getColNames() {
        return COL_NAMES;
    }
}
