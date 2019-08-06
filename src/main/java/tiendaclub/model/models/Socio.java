package tiendaclub.model.models;

import tiendaclub.data.DataStore;
import tiendaclub.model.models.abstracts.AbstractSocio;
import tiendaclub.model.models.abstracts.Persistible;
import tiendaclub.model.utils.DateUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Socio extends AbstractSocio {
    public static final String TABLE_NAME = "socios";
    private static final ArrayList<String> COL_NAMES = new ArrayList<>(Arrays.asList("dni", "nombre", "telefono", "direccion", "descripcion", "fecha_in", "fecha_active", "fecha_inactive"));

    private HashMap<Integer, Venta> ventas = new HashMap<>();

    public Socio(int id, String dni, String nombre, LocalDateTime fechaIn) {
        super(id, dni, nombre, fechaIn);
    }

    public Socio(String dni, String nombre) {
        super(dni, nombre);
    }

    public Socio(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getString(2), rs.getString(3), DateUtils.toLocalDateTime(rs.getTimestamp(7)));
        setFechaActive(DateUtils.toLocalDateTime(rs.getTimestamp(8)));
        setFechaInactive(DateUtils.toLocalDateTime(rs.getTimestamp(9)));
        setTelefono(rs.getString(4));
        setDireccion(rs.getString(5));
        setDescripcion(rs.getString(6));
    }

    @Override
    public void buildStatement(PreparedStatement pst) throws SQLException {
        pst.setString(1, dni);
        pst.setString(2, nombre);
        pst.setString(3, telefono);
        pst.setString(4, direccion);
        pst.setString(5, descripcion);
        pst.setTimestamp(6, DateUtils.toTimestamp(fechaIn));
        pst.setTimestamp(7, DateUtils.toTimestamp(fechaActive));
        pst.setTimestamp(8, DateUtils.toTimestamp(fechaInactive));
    }

    @Override
    public void updateObject(ResultSet rs) throws SQLException {
        //setId(rs.getInt(1));
        setDni(rs.getString(2));
        setNombre(rs.getString(3));
        setTelefono(rs.getString(4));
        setDireccion(rs.getString(5));
        setDescripcion(rs.getString(6));
        setFechaIn(DateUtils.toLocalDateTime(rs.getTimestamp(7)));
        setFechaActive(DateUtils.toLocalDateTime(rs.getTimestamp(8)));
        setFechaInactive(DateUtils.toLocalDateTime(rs.getTimestamp(9)));
    }

    public HashMap<Integer, Venta> getVentas() {
        return ventas;
    }

    @Override
    public int updateOnDb() {
        return DataStore.getSocios().update(this);
    }

    @Override
    public int refreshFromDb() {
        return DataStore.getSocios().updateObject(this);
    }

    @Override
    public String insertString() {
        return Persistible.buildInsertString(TABLE_NAME, COL_NAMES);
    }

    @Override
    public String updateString() {
        return Persistible.buildUpdateString(TABLE_NAME, ID_COL_NAME, COL_NAMES, getId());
    }
}
