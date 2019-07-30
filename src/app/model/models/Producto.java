package app.model.models;

import app.model.IPersistible;
import app.model.models.abstracts.AbstractProducto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Producto extends AbstractProducto implements IPersistible {
    public static final String TABLE_NAME = "productos";
    private static final ArrayList<String> COL_NAMES = new ArrayList<>(Arrays.asList("nombre", "descripcion", "precio_venta", "iva", "idCategoria"));

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
    }

    @Override
    public void buildStatement(PreparedStatement preparedStatement) throws SQLException {

    }

    @Override
    public void updateObject(ResultSet rs) throws SQLException {

    }

    @Override
    public void setIdCategoria(int idCategoria) {
        super.setIdCategoria(idCategoria);
        updateCategoria();
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    private void updateCategoria() {
        if (categoria != null)
            categoria.getProductos().remove(id);
        //TODO DAO
        //categoria = DAO categoria . get ( idCategoria );
        categoria.getProductos().put(id, this);
    }

    @Override
    public int updateOnDb() {
        return 0;
    }

    @Override
    public int refreshFromDb() {
        return 0;
    }

    @Override
    public String insertString() {
        return IPersistible.buildInsertString(TABLE_NAME, COL_NAMES);
    }

    @Override
    public String updateString() {
        return IPersistible.buildUpdateString(TABLE_NAME, ID_COL_NAME, COL_NAMES, getId());
    }
}
