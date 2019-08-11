package tiendaclub.model.models;

import tiendaclub.data.DataStore;
import tiendaclub.data.framework.dao.PersistibleDao;
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

    public Proveedor(int id, String nif, String nombre, boolean activo) {
        super(id, nif, nombre, activo);
    }

    public Proveedor(String nif, String nombre, boolean activo) {
        super(nif, nombre, activo);
    }

    public Proveedor(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4));
        setTelefono(rs.getString(4));
        setEmail(rs.getString(5));
        setDireccion(rs.getString(6));
        setDescripcion(rs.getString(7));
    }

    public void buildStatement(PreparedStatement pst) throws SQLException {
        pst.setString(1, nif);
        pst.setString(2, nombre);
        pst.setString(3, telefono);
        pst.setString(4, email);
        pst.setString(5, direccion);
        pst.setString(6, descripcion);
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
    public String getInsertString() {
        return PersistibleDao.buildInsertString(TABLE_NAME, COL_NAMES);
    }

    @Override
    public String getUpdateString() {
        return PersistibleDao.buildUpdateString(TABLE_NAME, ID_COL_NAME, COL_NAMES, getId());
    }

    @Override
    public int insertIntoDB() {
        return 0;
    }

    @Override
    public int updateOnDb() {
        return DataStore.getProveedores().update(this);
    }

    @Override
    public int refreshFromDb() {
        return DataStore.getProveedores().updateObject(this);
    }

    @Override
    public int deleteFromDb() {
        return 0;
    }
}
