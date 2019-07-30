package app.model.models;

import app.model.IPersistible;
import app.model.models.abstracts.AbstractSocio;
import app.model.utils.DateUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Socio extends AbstractSocio implements IPersistible {
    public static final String TABLE_NAME = "socios";
    private static final ArrayList<String> COL_NAMES = new ArrayList<>(Arrays.asList("dni", "nombre", "telefono", "direccion", "descripcion", "fecha_in", "fecha_active", "fecha_inactive"));

    private HashMap<Integer, Venta> ventas = new HashMap<>();

    public Socio(int id, String dni, String nombre, LocalDateTime fechaIn, LocalDateTime fechaActive, LocalDateTime fechaInactive) {
        super(id, dni, nombre, fechaIn, fechaActive, fechaInactive);
    }

    public Socio(String dni, String nombre) {
        super(dni, nombre);
    }

    public Socio(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getString(2), rs.getString(3), DateUtils.toLocalDateTime(rs.getDate(7)), DateUtils.toLocalDateTime(rs.getDate(8)), DateUtils.toLocalDateTime(rs.getDate(9)));
        setTelefono(rs.getString(4));
        setDireccion(rs.getString(5));
        setDescripcion(rs.getString(6));
    }

    @Override
    public void buildStatement(PreparedStatement preparedStatement) throws SQLException {

    }

    @Override
    public void updateObject(ResultSet rs) throws SQLException {

    }

    public HashMap<Integer, Venta> getVentas() {
        return ventas;
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
