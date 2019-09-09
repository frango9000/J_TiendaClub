package app.model;

import app.data.DataStore;
import app.data.appdao.CierreZDao;
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
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.NonNull;

public class CierreZ extends EntityInt {

    public static final String TABLE_NAME = "zs";
    private static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Arrays.asList("idCaja", "apertura", "idUsuarioApertura", "cierre", "idUsuarioCierre"));

    protected int idCaja;
    protected LocalDateTime apertura;
    protected int idUsuarioApertura;
    protected Usuario usuarioApertura;
    protected LocalDateTime cierre;
    protected Integer idUsuarioCierre;
    protected Usuario usuarioCierre;
    private Caja caja;

    public CierreZ() {
        super(0);
    }

    @Override
    public boolean setEntity(ResultSet resultSet) {
        try {
            setId(resultSet.getInt(1));
            setIdCaja(resultSet.getInt(2));
            setApertura(DateUtils.toLocalDateTime(resultSet.getTimestamp(3)));
            setIdUsuarioApertura(resultSet.getInt(4));
            setCierre(DateUtils.toLocalDateTime(resultSet.getDate(5)));
            setIdUsuarioCierre(resultSet.getInt(6));
            return true;
        } catch (SQLException e) {
            Flogger.atWarning().withCause(e).log();
            return false;
        }
    }

    @Override
    public boolean buildStatement(@NonNull PreparedStatement pst) {
        try {
            pst.setInt(1, getCaja().getId());
            pst.setTimestamp(2, DateUtils.toTimestamp(getApertura()));
            pst.setInt(3, getIdUsuarioApertura());
            pst.setTimestamp(4, DateUtils.toTimestamp(getCierre()));
            pst.setObject(5, getIdUsuarioCierre());
            return true;
        } catch (SQLException e) {
            Flogger.atWarning().withCause(e).log();
            return false;
        }
    }

    @Override
    public boolean restoreFrom(@NonNull IEntity objectV) {
        if (objectV.getClass().equals(getClass()) && getId() == objectV.getId() && !this.equals(objectV)) {
            CierreZ newValues = (CierreZ) objectV;
            setCaja(newValues.getCaja());
            setApertura(newValues.getApertura());
            setUsuarioApertura(newValues.getUsuarioApertura());
            setCierre(newValues.getCierre());
            setUsuarioCierre(newValues.getUsuarioCierre());
            return true;
        }
        return false;
    }


    @Override
    @SuppressWarnings("unchecked")
    public CierreZDao getDataStore() {
        return DataStore.getSessionStore().getCierreZs();
    }

    public int getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(int idCaja) {
        setCaja(DataStore.getSessionStore().getCajas().getById().getCacheValue(idCaja));
    }

    public LocalDateTime getApertura() {
        return apertura;
    }

    public void setApertura(LocalDateTime apertura) {
        this.apertura = apertura;
    }

    public LocalDateTime getCierre() {
        return cierre;
    }

    public void setCierre(LocalDateTime cierre) {
        this.cierre = cierre;
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja   = caja;
        this.idCaja = getCaja().getId();
    }

    public int getIdUsuarioApertura() {
        return idUsuarioApertura;
    }

    public void setIdUsuarioApertura(int idUsuarioApertura) {
        setUsuarioApertura(DataStore.getSessionStore().getUsuarios().getById().getCacheValue(idUsuarioApertura));
    }

    public Usuario getUsuarioApertura() {
        return usuarioApertura;
    }

    public void setUsuarioApertura(Usuario usuarioApertura) {
        this.usuarioApertura   = usuarioApertura;
        this.idUsuarioApertura = getUsuarioApertura().getId();
    }

    public Integer getIdUsuarioCierre() {
        return idUsuarioCierre;
    }

    public void setIdUsuarioCierre(Integer idUsuarioCierre) {
        setUsuarioCierre(DataStore.getSessionStore().getUsuarios().getById().getCacheValue(idUsuarioCierre));
    }

    public Usuario getUsuarioCierre() {
        return usuarioCierre;
    }

    public void setUsuarioCierre(Usuario usuarioCierre) {
        this.usuarioCierre = usuarioCierre;
        if (usuarioCierre != null)
            this.idUsuarioCierre = getUsuarioCierre().getId();
    }

    public Set<Venta> getVentas() {
        Set<Venta> ventas;
        if (getCierre() == null)
            ventas = DataStore.getSessionStore().getVentas().getIndexFecha().getCacheKeyGreaterThanValues(getApertura(), true);
        else
            ventas = DataStore.getSessionStore().getVentas().getIndexFecha().getCacheKeyIntervalValues(getApertura(), true, getCierre(), true);
        return ventas.stream().filter(venta -> venta.getCaja().equals(this.getCaja())).collect(Collectors.toSet());
    }

    @Override
    public ArrayList<String> getColumnNames() {
        return COLUMN_NAMES;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CierreZ cierreZ = (CierreZ) o;
        return getId() == cierreZ.getId() && getIdCaja() == cierreZ.getIdCaja()
               && Objects.equal(getCaja(), cierreZ.getCaja()) && Objects.equal(getApertura(), cierreZ.getApertura())
               && Objects.equal(getCierre(), cierreZ.getCierre());
    }

    @Override
    public String fullToString() {
        return MoreObjects.toStringHelper(this)
                          .add("id", id)
                          .add("caja", caja)
                          .add("apertura", apertura)
                          .add("usuarioApertura", usuarioApertura.fullToString())
                          .add("cierre", cierre)
                          .add("usuarioCierre", usuarioCierre)
                          .toString();
    }

    @Override
    public String toString() {
        return getId() + " C:" + getIdCaja() + " " + apertura.toString();
    }


}
