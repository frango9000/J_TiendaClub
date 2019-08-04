package tiendaclub.model.models;

import tiendaclub.model.DataStore;
import tiendaclub.model.IPersistible;
import tiendaclub.model.models.abstracts.AbstractVenta;
import tiendaclub.model.utils.DateUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Venta extends AbstractVenta implements IPersistible {
    public static final String TABLE_NAME = "ventas";
    private static final ArrayList<String> COL_NAMES = new ArrayList<>(Arrays.asList("idUsuario", "idCaja", "idSocio", "fechahora"));

    private Usuario usuario;
    private Caja caja;
    private Socio socio;

    private HashMap<Integer, Vendido> vendidos = new HashMap<>();

    public Venta(int id, int idUsuario, int idCaja, int idSocio, LocalDateTime fechahora) {
        super(id, idUsuario, idCaja, idSocio, fechahora);
        updateUsuario();
        updateCaja();
        updateSocio();
    }

    public Venta(int idUsuario, int idCaja, int idSocio, LocalDateTime fechahora) {
        super(idUsuario, idCaja, idSocio, fechahora);
        updateUsuario();
        updateCaja();
        updateSocio();
    }

    public Venta(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), DateUtils.toLocalDateTime(rs.getDate(5)));
    }

    @Override
    public void buildStatement(PreparedStatement pst) throws SQLException {
        pst.setInt(1, idUsuario);
        pst.setInt(2, idCaja);
        pst.setInt(3, idSocio);
        pst.setTimestamp(4, DateUtils.toTimestamp(fechahora));
    }

    @Override
    public void updateObject(ResultSet rs) throws SQLException {
        //setId(rs.getInt(1));
        setIdUsuario(rs.getInt(2));
        setIdCaja(rs.getInt(3));
        setIdSocio(rs.getInt(4));
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
            usuario.getVentas().remove(id);
        this.usuario = usuario2;
        if (usuario != null)
            usuario.getVentas().put(id, this);
    }

    private void updateUsuario() {
        setUsuario(DataStore.getUsuarios().get(idUsuario));
    }

    @Override
    public void setIdCaja(int idCaja) {
        super.setIdCaja(idCaja);
        updateCaja();
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja2) {
        if (caja != null)
            caja.getVentas().remove(id);
        this.caja = caja2;
        if (caja != null)
            caja.getVentas().put(id, this);
    }

    private void updateCaja() {
        setCaja(DataStore.getCajas().get(idCaja));
    }

    @Override
    public void setIdSocio(int idSocio) {
        super.setIdSocio(idSocio);
        updateSocio();
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio2) {
        if (socio != null)
            socio.getVentas().remove(id);
        this.socio = socio2;
        if (socio != null)
            socio.getVentas().put(id, this);
    }

    private void updateSocio() {
        setSocio(DataStore.getSocios().get(idSocio));
    }

    public HashMap<Integer, Vendido> getVendidos() {
        return vendidos;
    }

    @Override
    public int updateOnDb() {
        return DataStore.getVentas().update(this);
    }

    @Override
    public int refreshFromDb() {
        return DataStore.getVentas().updateObject(this);
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
