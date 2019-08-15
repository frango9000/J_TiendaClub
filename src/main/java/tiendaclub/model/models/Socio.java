package tiendaclub.model.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import tiendaclub.model.models.abstracts.AbstractSocio;
import tiendaclub.model.utils.DateUtils;

public class Socio extends AbstractSocio {

    public static final String TABLE_NAME = "socios";
    private static final ArrayList<String> COL_NAMES = new ArrayList<>(
            Arrays.asList("dni", "nombre", "telefono", "direccion", "descripcion", "fecha_in", "activo"));

    private HashMap<Integer, Venta> ventas = new HashMap<>();

    public Socio(int id, String dni, String nombre, LocalDateTime fechaIn) {
        super(id, dni, nombre, fechaIn);
    }

    public Socio(String dni, String nombre) {
        super(dni, nombre);
    }

    public Socio(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getString(2), rs.getString(3), DateUtils.toLocalDateTime(rs.getTimestamp(7)));
        setTelefono(rs.getString(4));
        setDireccion(rs.getString(5));
        setDescripcion(rs.getString(6));
        setActivo(rs.getBoolean(8));
    }

    @Override
    public void buildStatement(PreparedStatement pst) throws SQLException {
        pst.setString(1, getDni());
        pst.setString(2, getNombre());
        pst.setString(3, getTelefono());
        pst.setString(4, getDireccion());
        pst.setString(5, getDescripcion());
        pst.setTimestamp(6, DateUtils.toTimestamp(getFechaIn()));
        pst.setBoolean(7, isActivo());
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
        setActivo(rs.getBoolean(8));
    }

    public HashMap<Integer, Venta> getVentas() {
        return ventas;
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
