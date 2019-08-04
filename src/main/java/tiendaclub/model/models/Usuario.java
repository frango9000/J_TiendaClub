package tiendaclub.model.models;

import tiendaclub.model.DataStore;
import tiendaclub.model.IPersistible;
import tiendaclub.model.models.abstracts.AbstractUsuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Usuario extends AbstractUsuario implements IPersistible {
    public static final String TABLE_NAME = "usuarios";
    private static final ArrayList<String> COL_NAMES = new ArrayList<>(Arrays.asList("user", "pass", "nombre", "telefono", "email", "direccion", "descripcion", "idAcceso"));

    private Acceso acceso;

    private HashMap<Integer, Compra> compras = new HashMap<>();
    private HashMap<Integer, Venta> ventas = new HashMap<>();
    private HashMap<Integer, Transferencia> transferencias = new HashMap<>();

    public Usuario(int id, String user, String pass, String nombre, int idAcceso) {
        super(id, user, pass, nombre, idAcceso);
        updateAcceso();
    }

    public Usuario(String user, String pass, String nombre, int idAcceso) {
        super(user, pass, nombre, idAcceso);
        updateAcceso();
    }

    public Usuario(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(9));
        setTelefono(rs.getString(5));
        setEmail(rs.getString(6));
        setDireccion(rs.getString(7));
        setDescripcion(rs.getString(8));
    }

    public void buildStatement(PreparedStatement pst) throws SQLException {
        pst.setString(1, user);
        pst.setString(2, pass);
        pst.setString(3, nombre);
        pst.setString(4, telefono);
        pst.setString(5, email);
        pst.setString(6, direccion);
        pst.setString(7, descripcion);
        pst.setInt(8, idAcceso);
    }

    public void updateObject(ResultSet rs) throws SQLException {
        //setId(rs.getInt(1));
        setUser(rs.getString(2));
        setPass(rs.getString(3));
        setNombre(rs.getString(4));
        setTelefono(rs.getString(5));
        setEmail(rs.getString(6));
        setDireccion(rs.getString(7));
        setDescripcion(rs.getString(8));
        setIdAcceso(rs.getInt(9));
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
    public int updateOnDb() {
        return DataStore.getUsuarios().update(this);
    }

    @Override
    public int refreshFromDb() {
        return DataStore.getUsuarios().updateObject(this);
    }
}
