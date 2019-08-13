package tiendaclub.model.models;

import tiendaclub.data.DataStore;
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

    public Usuario(int id, String username, String pass, int idAcceso) {
        super(id, username, pass, idAcceso);
        updateAcceso();
    }

    public Usuario(String username, String pass, int idAcceso) {
        super(username, pass, idAcceso);
        updateAcceso();
    }

    public Usuario(String username, String pass, Acceso acceso) {
        super(username, pass, acceso.getId());
        setAcceso(acceso);
    }

    public Usuario(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(9));
        setNombre(rs.getString(4));
        setTelefono(rs.getString(5));
        setEmail(rs.getString(6));
        setDireccion(rs.getString(7));
        setDescripcion(rs.getString(8));
        setActivo(rs.getBoolean(10));
    }


    public void buildStatement(PreparedStatement pst) throws SQLException {
        pst.setString(1, getUsername());
        pst.setString(2, getPass());
        pst.setString(3, getNombre());
        pst.setString(4, getTelefono());
        pst.setString(5, getEmail());
        pst.setString(6, getDireccion());
        pst.setString(7, getDescripcion());
        pst.setInt(8, getIdAcceso());
        pst.setBoolean(9, isActivo());
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
    public void setIdAcceso(int idAcceso) {
        super.setIdAcceso(idAcceso);
        updateAcceso();
    }

    public Acceso getAcceso() {
        return acceso;
    }

    public void setAcceso(Acceso acceso2) {
        if (acceso != null)
            acceso.getUsuarios().remove(getId());
        this.acceso = acceso2;
        if (acceso != null)
            acceso.getUsuarios().put(getId(), this);
    }

    private void updateAcceso() {
        setAcceso(DataStore.getAccesos().get(getIdAcceso()));
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
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public ArrayList<String> getColNames() {
        return COL_NAMES;
    }
}
