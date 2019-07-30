package app.model.models;

import app.model.IPersistible;
import app.model.models.abstracts.AbstractCaja;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Caja extends AbstractCaja implements IPersistible {
    public static final String TABLE_NAME = "cajas";
    private static final ArrayList<String> COL_NAMES = new ArrayList<>(Arrays.asList("idSede", "nombre"));

    private Sede sede;

    private HashMap<Integer, CierreZ> cierresZs = new HashMap<>();
    private HashMap<Integer, Venta> ventas = new HashMap<>();

    public Caja(int id, int idSede, String nombre) {
        super(id, idSede, nombre);
        updateSede();
    }

    public Caja(int idSede, String nombre) {
        super(idSede, nombre);
        updateSede();
    }

    public Caja(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getInt(2), rs.getString(3));
    }

    @Override
    public void buildStatement(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, idSede);
        preparedStatement.setString(2, nombre);
    }

    @Override
    public void updateObject(ResultSet rs) throws SQLException {
        //setId(rs.getInt(1));
        setIdSede(rs.getInt(2));
        setNombre(rs.getString(3));
    }

    @Override
    public String insertString() {
        return IPersistible.buildInsertString(TABLE_NAME, COL_NAMES);
    }

    @Override
    public String updateString() {
        return IPersistible.buildUpdateString(TABLE_NAME, ID_COL_NAME, COL_NAMES, getId());
    }

    @Override
    public void setIdSede(int idSede) {
        super.setIdSede(idSede);
        updateSede();
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    private void updateSede() {
        if (sede != null)
            sede.getCajas().remove(id);
        //TODO DAO
        //sede = DAO sede . get ( idSede );
        sede.getCajas().put(id, this);
    }

    public HashMap<Integer, CierreZ> getCierresZs() {
        return cierresZs;
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
}
