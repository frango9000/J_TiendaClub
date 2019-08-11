package tiendaclub.model.models;

import tiendaclub.data.DataStore;
import tiendaclub.data.framework.dao.PersistibleDao;
import tiendaclub.model.models.abstracts.AbstractUsuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Usuario extends AbstractUsuario {
    public static final String TABLE_NAME = "usuarios";
    public static final ArrayList<String> COL_NAMES = new ArrayList<>(Arrays.asList("username", "pass", "nombre", "telefono", "email", "direccion", "descripcion", "idAcceso", "activo"));

    private Acceso acceso;

    private HashMap<Integer, Compra> compras = new HashMap<>();
    private HashMap<Integer, Venta> ventas = new HashMap<>();
    private HashMap<Integer, Transferencia> transferencias = new HashMap<>();

    public Usuario(int id, String username, String pass, int idAcceso, boolean activo) {
        super(id, username, pass, idAcceso, activo);
        updateAcceso();
    }

    public Usuario(String username, String pass, int idAcceso, boolean activo) {
        super(username, pass, idAcceso, activo);
        updateAcceso();
    }

    public Usuario(String username, String pass, Acceso acceso, boolean activo) {
        super(username, pass, acceso.getId(), activo);
        setAcceso(acceso);
    }

    public Usuario(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(9), rs.getBoolean(10));
        setNombre(rs.getString(4));
        setTelefono(rs.getString(5));
        setEmail(rs.getString(6));
        setDireccion(rs.getString(7));
        setDescripcion(rs.getString(8));
    }


    public void buildStatement(PreparedStatement pst) throws SQLException {
        pst.setString(1, username);
        pst.setString(2, pass);
        pst.setString(3, nombre);
        pst.setString(4, telefono);
        pst.setString(5, email);
        pst.setString(6, direccion);
        pst.setString(7, descripcion);
        pst.setInt(8, idAcceso);
        pst.setBoolean(9, activo);
    }

    public void updateObject(ResultSet rs) throws SQLException {
        //setId(rs.getInt(1));
        setUsername(rs.getString(2));
        setPass(rs.getString(3));
        setNombre(rs.getString(4));
        setTelefono(rs.getString(5));
        setEmail(rs.getString(6));
        setDireccion(rs.getString(7));
        setDescripcion(rs.getString(8));
        setIdAcceso(rs.getInt(9));
        setActivo(rs.getBoolean(10));
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
    public void setIdAcceso(int idAcceso) {
        super.setIdAcceso(idAcceso);
        updateAcceso();
    }

    public Acceso getAcceso() {
        return acceso;
    }

    public void setAcceso(Acceso acceso2) {
        if (acceso != null)
            acceso.getUsuarios().remove(id);
        this.acceso = acceso2;
        if (acceso != null)
            acceso.getUsuarios().put(id, this);
    }

    private void updateAcceso() {
        setAcceso(DataStore.getAccesos().get(idAcceso));
    }

    public HashMap<Integer, Compra> getCompras() {
        return compras;
    }

    public HashMap<Integer, Venta> getVentas() {
        return ventas;
    }

    public HashMap<Integer, Transferencia> getTransferencias() {
        return transferencias;
    }

    @Override
    public int insertIntoDB() {
        if (id == 0) {
            return DataStore.getUsuarios().insert(this);
        } else return 0;
    }

    @Override
    public int updateOnDb() {
        return DataStore.getUsuarios().update(this);
    }

    @Override
    public int refreshFromDb() {
        return DataStore.getUsuarios().updateObject(this);
    }

    @Override
    public int deleteFromDb() {
        return DataStore.getUsuarios().delete(this);
    }
}
