package tiendaclub.model.models;

import tiendaclub.model.DataStore;
import tiendaclub.model.IPersistible;
import tiendaclub.model.models.abstracts.AbstractCategoria;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Categoria extends AbstractCategoria implements IPersistible {
    public static final String TABLE_NAME = "categorias";
    private static final ArrayList<String> COL_NAMES = new ArrayList<>(Collections.singletonList("nombre"));

    private HashMap<Integer, Producto> productos = new HashMap<>();

    public Categoria(int id, String nombre) {
        super(id, nombre);
    }

    public Categoria(String nombre) {
        super(nombre);
    }

    public Categoria(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getString(2));
    }

    @Override
    public void buildStatement(PreparedStatement pst) throws SQLException {
        pst.setString(1, nombre);
    }

    @Override
    public void updateObject(ResultSet rs) throws SQLException {
        //setId(rs.getInt(1));
        setNombre(rs.getString(2));

    }

    public HashMap<Integer, Producto> getProductos() {
        return productos;
    }

    @Override
    public int updateOnDb() {
        return DataStore.getCategorias().update(this);
    }

    @Override
    public int refreshFromDb() {
        return DataStore.getCategorias().updateObject(this);
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
