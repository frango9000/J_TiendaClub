package tiendaclub.model.models;

import tiendaclub.data.DataStore;
import tiendaclub.data.GenericDao;
import tiendaclub.model.models.abstracts.AbstractSede;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Sede extends AbstractSede {
    public static final String TABLE_NAME = "sedes";
    private static final ArrayList<String> COL_NAMES = new ArrayList<>(Arrays.asList("nombre", "telefono", "direccion", "activo"));

    private HashMap<Integer, Caja> cajas = new HashMap<>();
    private HashMap<Integer, Compra> compras = new HashMap<>();
    private HashMap<Integer, Transferencia> transferIn = new HashMap<>();
    private HashMap<Integer, Transferencia> transferOut = new HashMap<>();

    public Sede(int id, String nombre, String telefono, String direccion, boolean activo) {
        super(id, nombre, telefono, direccion, activo);
    }

    public Sede(String nombre, String telefono, String direccion, boolean activo) {
        super(nombre, telefono, direccion, activo);
    }

    public Sede(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5));
    }

    @Override
    public void buildStatement(PreparedStatement pst) throws SQLException {
        pst.setString(1, nombre);
        pst.setString(2, telefono);
        pst.setString(3, direccion);
        pst.setBoolean(4, activo);
    }

    @Override
    public void updateObject(ResultSet rs) throws SQLException {
        //setId(rs.getInt(1));
        setNombre(rs.getString(2));
        setTelefono(rs.getString(3));
        setDireccion(rs.getString(4));
        setActivo(rs.getBoolean(5));
    }

    public HashMap<Integer, Caja> getCajas() {
        return cajas;
    }

    public HashMap<Integer, Compra> getCompras() {
        return compras;
    }

    public HashMap<Integer, Transferencia> getTransferIn() {
        return transferIn;
    }

    public HashMap<Integer, Transferencia> getTransferOut() {
        return transferOut;
    }

    @Override
    public int insertIntoDB() {
        return 0;
    }

    @Override
    public int updateOnDb() {
        return DataStore.getSedes().update(this);
    }

    @Override
    public int refreshFromDb() {
        return DataStore.getSedes().updateObject(this);
    }

    @Override
    public int deleteFromDb() {
        return 0;
    }

    @Override
    public String getInsertString() {
        return GenericDao.buildInsertString(TABLE_NAME, COL_NAMES);
    }

    @Override
    public String getUpdateString() {
        return GenericDao.buildUpdateString(TABLE_NAME, ID_COL_NAME, COL_NAMES, getId());
    }

    @Override
    public String toString() {
        return id + " " + nombre;
    }
}
