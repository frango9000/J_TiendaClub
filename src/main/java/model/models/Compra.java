package model.models;

import model.DataStore;
import model.IPersistible;
import model.models.abstracts.AbstractCompra;
import model.utils.DateUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Compra extends AbstractCompra implements IPersistible {
    public static final String TABLE_NAME = "compras";
    private static final ArrayList<String> COL_NAMES = new ArrayList<>(Arrays.asList("idUsuario", "idSede", "idProveedor", "fechahora"));

    private Usuario usuario;
    private Proveedor proveedor;
    private Sede sede;

    private HashMap<Integer, Comprado> comprados = new HashMap<>();

    public Compra(int id, int idUsuario, int idSede, int idProveedor, LocalDateTime fechahora) {
        super(id, idUsuario, idSede, idProveedor, fechahora);
        updateUsuario();
        updateProveedor();
        updateSede();
    }

    public Compra(int idUsuario, int idSede, int idProveedor, LocalDateTime fechahora) {
        super(idUsuario, idSede, idProveedor, fechahora);
        updateUsuario();
        updateProveedor();
        updateSede();
    }

    public Compra(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), DateUtils.toLocalDateTime(rs.getDate(5)));
    }

    @Override
    public void buildStatement(PreparedStatement pst) throws SQLException {
        pst.setInt(1, idUsuario);
        pst.setInt(2, idSede);
        pst.setInt(3, idProveedor);
        pst.setTimestamp(4, DateUtils.toTimestamp(fechahora));
    }

    @Override
    public void updateObject(ResultSet rs) throws SQLException {
        //setId(rs.getInt(1));
        setIdUsuario(rs.getInt(2));
        setIdSede(rs.getInt(3));
        setIdProveedor(rs.getInt(4));
        setFechahora(DateUtils.toLocalDateTime(rs.getTimestamp(5)));
    }

    @Override
    public void setIdUsuario(int idUsuario) {
        super.setIdUsuario(idUsuario);
        updateUsuario();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario2) {
        if (usuario != null)
            usuario.getCompras().remove(id);
        this.usuario = usuario2;
        if (usuario != null)
            usuario.getCompras().put(id, this);
    }

    private void updateUsuario() {
        setUsuario(DataStore.getUsuarios().get(idUsuario));
    }

    @Override
    public void setIdProveedor(int idProveedor) {
        super.setIdProveedor(idProveedor);
        updateProveedor();
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor2) {
        if (proveedor != null)
            proveedor.getCompras().remove(id);
        this.proveedor = proveedor2;
        if (proveedor != null)
            proveedor.getCompras().put(id, this);
    }

    private void updateProveedor() {
        setProveedor(DataStore.getProveedores().get(idProveedor));
    }

    @Override
    public void setIdSede(int idSede) {
        super.setIdSede(idSede);
        updateSede();
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede2) {
        if (sede != null)
            sede.getCompras().remove(id);
        this.sede = sede2;
        if (sede != null)
            sede.getCompras().put(id, this);
    }

    private void updateSede() {
        setSede(DataStore.getSedes().get(idSede));
    }

    public HashMap<Integer, Comprado> getComprados() {
        return comprados;
    }

    @Override
    public int updateOnDb() {
        return DataStore.getCompras().update(this);
    }

    @Override
    public int refreshFromDb() {
        return DataStore.getCompras().updateObject(this);
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
