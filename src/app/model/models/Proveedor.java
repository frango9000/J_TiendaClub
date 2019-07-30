package app.model.models;

import app.model.IPersistible;
import app.model.models.abstracts.AbstractProveedor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Proveedor extends AbstractProveedor implements IPersistible {
    public static final String TABLE_NAME = "proveedores";
    private static final ArrayList<String> COL_NAMES = new ArrayList<>(Arrays.asList("nif", "nombre", "telefono", "email", "direccion", "descripcion"));

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
    }

    public void buildStatement(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, nif);
        preparedStatement.setString(2, nombre);
        preparedStatement.setString(3, telefono);
        preparedStatement.setString(4, email);
        preparedStatement.setString(5, direccion);
        preparedStatement.setString(6, descripcion);
    }

    public void updateObject(ResultSet rs) throws SQLException {
        //setId(rs.getInt(1));
        setNif(rs.getString(2));
        setNombre(rs.getString(3));
        setTelefono(rs.getString(4));
        setEmail(rs.getString(5));
        setDireccion(rs.getString(6));
        setDescripcion(rs.getString(7));
    }

    @Override
    public String insertString() {
        return IPersistible.buildInsertString(TABLE_NAME, COL_NAMES);
    }

    @Override
    public String updateString() {
        return IPersistible.buildUpdateString(TABLE_NAME, ID_COL_NAME, COL_NAMES, getId());
    }

    public HashMap<Integer, Compra> getCompras() {
        return compras;
    }

    @Override
    public int updateOnDb() {
        return 0;
    }

    @Override
    public int refreshFromDb() {
        return 0;
    }
}
