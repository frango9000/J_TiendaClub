package app.model;

import app.data.DataStore;
import app.data.appdao.VentaDao;
import app.data.casteldao.daomodel.IPersistible;
import app.data.casteldao.daomodel.Persistible;
import app.misc.DateUtils;
import com.google.common.base.Objects;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import org.checkerframework.checker.nullness.qual.NonNull;

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

    public Venta(int id) {
        super(id);
    }

    public Venta() {
        this(0);
    }

    public Venta(ResultSet rs) throws SQLException {
        this(rs.getInt(1));
        setIdUsuario(rs.getInt(2));
        setIdCaja(rs.getInt(3));
        setIdSocio(rs.getInt(4));
        setFechahora(DateUtils.toLocalDateTime(rs.getDate(5)));
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
        setUsuario(DataStore.getUsuarios().getById().getCacheValue(getIdUsuario()));
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja   = caja;
        this.idCaja = getCaja().getId();
    }

    private void updateCaja() {
        setCaja(DataStore.getCajas().getById().getCacheValue(getIdCaja()));
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio   = socio;
        this.idSocio = getSocio().getId();
    }

    private void updateSocio() {
        setSocio(DataStore.getSocios().getById().getCacheValue(getIdSocio()));
    }

    public Set<Vendido> getVendidos() {
        return DataStore.getVendidos().getIndexVenta().getCacheKeyValues(this);
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
