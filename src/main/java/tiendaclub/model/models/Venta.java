package tiendaclub.model.models;

import com.google.common.base.Objects;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import org.checkerframework.checker.nullness.qual.NonNull;
import tiendaclub.data.DataStore;
import tiendaclub.data.dao.VentaDao;
import tiendaclub.data.framework.model.IPersistible;
import tiendaclub.data.framework.model.Persistible;
import tiendaclub.misc.DateUtils;

public class Venta extends Persistible {

    public static final String TABLE_NAME = "ventas";
    private static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Arrays.asList("idUsuario", "idCaja", "idSocio", "fechahora"));


    protected int idUsuario;
    protected int idCaja;
    protected int idSocio;
    protected LocalDateTime fechahora;
    private Usuario usuario;
    private Caja caja;
    private Socio socio;

    public Venta(int id, int idUsuario, int idCaja, int idSocio, LocalDateTime fechahora) {
        super(id);
        setIdUsuario(idUsuario);
        setIdCaja(idCaja);
        setIdSocio(idSocio);
        setFechahora(fechahora);
    }

    public Venta(int idUsuario, int idCaja, int idSocio, LocalDateTime fechahora) {
        this(0, idUsuario, idCaja, idSocio, fechahora);
    }

    public Venta(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), DateUtils.toLocalDateTime(rs.getDate(5)));
    }

    public Venta(Usuario usuario, Caja caja, Socio socio, LocalDateTime fechahora) {
        super(0);
        setUsuario(usuario);
        setCaja(caja);
        setSocio(socio);
        setFechahora(fechahora);
    }

    @Override
    public void buildStatement(@NonNull PreparedStatement pst) throws SQLException {
        pst.setInt(1, getIdUsuario());
        pst.setInt(2, getIdCaja());
        pst.setInt(3, getIdSocio());
        pst.setTimestamp(4, DateUtils.toTimestamp(getFechahora()));
    }

    @Override
    public <V extends IPersistible> boolean restoreFrom(@NonNull V objectV) {
        if (getId() == objectV.getId() && !this.equals(objectV)) {
            Venta newValues = (Venta) objectV;
            setUsuario(newValues.getUsuario());
            setCaja(newValues.getCaja());
            setSocio(newValues.getSocio());
            setFechahora(newValues.getFechahora());
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<String> getColumnNames() {
        return COLUMN_NAMES;
    }

    @Override
    @SuppressWarnings("unchecked")
    public VentaDao getDataStore() {
        return DataStore.getVentas();
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
        updateUsuario();
    }

    public int getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
        updateCaja();
    }

    public int getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(int idSocio) {
        this.idSocio = idSocio;
        updateSocio();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario   = usuario;
        this.idUsuario = getUsuario().getId();
    }

    public LocalDateTime getFechahora() {
        return fechahora;
    }

    public void setFechahora(LocalDateTime fechahora) {
        this.fechahora = fechahora;
    }

    private void updateUsuario() {
        setUsuario(DataStore.getUsuarios().getIndexId().getCacheValue(getIdUsuario()));
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja   = caja;
        this.idCaja = getCaja().getId();
    }

    private void updateCaja() {
        setCaja(DataStore.getCajas().getIndexId().getCacheValue(getIdCaja()));
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio   = socio;
        this.idSocio = getSocio().getId();
    }

    private void updateSocio() {
        setSocio(DataStore.getSocios().getIndexId().getCacheValue(getIdSocio()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Venta venta = (Venta) o;
        return getId() == venta.getId() && getIdUsuario() == venta.getIdUsuario() && getIdCaja() == venta.getIdCaja()
               && getIdSocio() == venta.getIdSocio() && Objects.equal(getFechahora(), venta.getFechahora());
    }

    @Override
    public int hashCode() {
        return getId();
    }
}
