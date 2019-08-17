package tiendaclub.model.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import tiendaclub.model.models.abstracts.AbstractCategoria;

public class Categoria extends AbstractCategoria {

    public static final String TABLE_NAME = "categorias";
    private static final ArrayList<String> COL_NAMES = new ArrayList<>(Arrays.asList("nombre", "activo"));

    private HashMap<Integer, Producto> productos = new HashMap<>();

    public Categoria(int id, String nombre) {
        super(id, nombre);
    }

    public Categoria(String nombre) {
        super(nombre);
    }

    public Categoria(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getString(2));
        setActivo(rs.getBoolean(3));
    }

    @Override
    public void buildStatement(PreparedStatement pst) throws SQLException {
        pst.setString(1, getNombre());
        pst.setBoolean(2, isActivo());
    }

    @Override
    public void updateObject(ResultSet rs) throws SQLException {
        //setId(rs.getInt(1));
        setNombre(rs.getString(2));
        setActivo(rs.getBoolean(3));
    }

    public HashMap<Integer, Producto> getProductos() {
        return productos;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public ArrayList<String> getColumnNames() {
        return COL_NAMES;
    }
}
