package tiendaclub.model.models;

import tiendaclub.model.models.abstracts.AbstractProveedor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Proveedor extends AbstractProveedor {
    public static final String TABLE_NAME = "proveedores";
    private static final ArrayList<String> COL_NAMES = new ArrayList<>(Arrays.asList("nif", "nombre", "telefono", "email", "direccion", "descripcion", "activo"));

    private HashMap<Integer, Compra> compras = new HashMap<>();

    public Proveedor(int id, String nif, String nombre) {
        super(id, nif, nombre);
    }

    public Proveedor(String nif, String nombre) {
        super(nif, nombre);
    }

    public Proveedor(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getString(2), rs.getString(3));
        setTelefono(rs.getString(4));
        setEmail(rs.getString(5));
        setDireccion(rs.getString(6));
        setDescripcion(rs.getString(7));
        setActivo(rs.getBoolean(8));
    }

    public void buildStatement(PreparedStatement pst) throws SQLException {
        pst.setString(1, getNif());
        pst.setString(2, getNombre());
        pst.setString(3, getTelefono());
        pst.setString(4, getEmail());
        pst.setString(5, getDireccion());
        pst.setString(6, getDescripcion());
        pst.setBoolean(7, isActivo());
    }

    public void updateObject(ResultSet rs) throws SQLException {
        //setId(rs.getInt(1));
        setNif(rs.getString(2));
        setNombre(rs.getString(3));
        setTelefono(rs.getString(4));
        setEmail(rs.getString(5));
        setDireccion(rs.getString(6));
        setDescripcion(rs.getString(7));
        setActivo(rs.getBoolean(8));
    }

    public HashMap<Integer, Compra> getCompras() {
        return compras;
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
