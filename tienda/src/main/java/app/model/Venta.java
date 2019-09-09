package app.model;

import app.data.DataStore;
import app.data.appdao.VentaDao;
import app.misc.DateUtils;
import app.misc.Flogger;
import casteldao.model.EntityInt;
import casteldao.model.IEntity;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import org.checkerframework.checker.nullness.qual.NonNull;

public class Venta extends EntityInt {

    public static final String TABLE_NAME = "ventas";
    private static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Arrays.asList("idUsuario", "idCaja", "idSocio", "fechahora"));


    protected int idUsuario;
    protected int idCaja;
    protected int idSocio;
    protected LocalDateTime fechahora;
    private Usuario usuario;
    private Caja caja;
    private Socio socio;

    public Venta() {
        super(0);
    }

    @Override
    public boolean setEntity(@NonNull ResultSet rs) {
        try {
            setId(rs.getInt(1));
            setIdUsuario(rs.getInt(2));
            setIdCaja(rs.getInt(3));
            setIdSocio(rs.getInt(4));
            setFechahora(DateUtils.toLocalDateTime(rs.getDate(5)));
            return true;
        } catch (SQLException e) {
            Flogger.atWarning().withCause(e).log();
            return false;
        }
    }

    @Override
    public boolean buildStatement(@NonNull PreparedStatement pst) {
        try {
            pst.setInt(1, getIdUsuario());
            pst.setInt(2, getIdCaja());
            pst.setInt(3, getIdSocio());
            pst.setTimestamp(4, DateUtils.toTimestamp(getFechahora()));
            return true;
        } catch (SQLException e) {
            Flogger.atWarning().withCause(e).log();
            return false;
        }
    }


    @Override
    public boolean restoreFrom(@NonNull IEntity objectV) {
        if (objectV.getClass().equals(getClass()) && getId() == objectV.getId() && !this.equals(objectV)) {
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
        return DataStore.getSessionStore().getVentas();
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        setUsuario(DataStore.getSessionStore().getUsuarios().getById().getCacheValue(idUsuario));
    }

    public int getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(int idCaja) {
        setCaja(DataStore.getSessionStore().getCajas().getById().getCacheValue(idCaja));
    }

    public int getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(int idSocio) {
        setSocio(DataStore.getSessionStore().getSocios().getById().getCacheValue(idSocio));
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

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja   = caja;
        this.idCaja = getCaja().getId();
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio   = socio;
        this.idSocio = getSocio().getId();
    }

    public Set<Vendido> getVendidos() {
        return DataStore.getSessionStore().getVendidos().getIndexVenta().getCacheKeyValues(this);
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
        return getId().equals(venta.getId())
               && getIdUsuario() == venta.getIdUsuario()
               && getIdCaja() == venta.getIdCaja()
               && getIdSocio() == venta.getIdSocio()
               && Objects.equal(getFechahora(), venta.getFechahora());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("id", id)
                          .add("fechahora", fechahora)
                          .add("usuario", usuario)
                          .add("caja", caja)
                          .add("socio", socio)
                          .toString();
    }


    @Override
    public String fullToString() {
        return getId() + " " + idUsuario + " " + idCaja + " " + idSocio;
    }
}
