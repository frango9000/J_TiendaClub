package tiendaclub.model.models;

import tiendaclub.data.DataStore;
import tiendaclub.model.models.abstracts.AbstractCompra;
import tiendaclub.model.utils.DateUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Compra extends AbstractCompra {
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
        pst.setInt(1, getIdUsuario());
        pst.setInt(2, getIdSede());
        pst.setInt(3, getIdProveedor());
        pst.setTimestamp(4, DateUtils.toTimestamp(getFechahora()));
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
            usuario.getCompras().remove(getId());
        this.usuario = usuario2;
        if (usuario != null)
            usuario.getCompras().put(getId(), this);
    }

    private void updateUsuario() {
        setUsuario(DataStore.getUsuarios().getIdIndex().getCacheValue(getIdUsuario()));
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
            proveedor.getCompras().remove(getId());
        this.proveedor = proveedor2;
        if (proveedor != null)
            proveedor.getCompras().put(getId(), this);
    }

    private void updateProveedor() {
        setProveedor(DataStore.getProveedores().getIdIndex().getCacheValue(getIdProveedor()));
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
            sede.getCompras().remove(getId());
        this.sede = sede2;
        if (sede != null)
            sede.getCompras().put(getId(), this);
    }

    private void updateSede() {
        setSede(DataStore.getSedes().getIdIndex().getCacheValue(getIdSede()));
    }

    public HashMap<Integer, Comprado> getComprados() {
        return comprados;
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
