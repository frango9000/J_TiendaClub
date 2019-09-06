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
import org.checkerframework.checker.nullness.qual.NonNull;

public class CierreZ extends EntityInt {

    public static final String TABLE_NAME = "zs";
    private static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Arrays.asList("idCaja", "apertura", "cierre"));

    protected int idCaja;
    protected LocalDateTime apertura;
    protected LocalDateTime cierre;
    private Caja caja;

    public CierreZ() {
        super(0);
    }

    public CierreZ(int id, int idCaja, LocalDateTime apertura) {
        super(id);
        setIdCaja(idCaja);
        setApertura(apertura);
    }

    public CierreZ(int idCaja, LocalDateTime apertura) {
        this(0, idCaja, apertura);
    }

    public CierreZ(Caja caja, LocalDateTime apertura) {
        super(0);
        this.caja     = caja;
        this.apertura = apertura;
    }

    @Override
    public boolean setEntity(ResultSet resultSet) {
        try {
            setId(resultSet.getInt(1));
            setIdCaja(resultSet.getInt(2));
            setApertura(DateUtils.toLocalDateTime(resultSet.getTimestamp(3)));
            setCierre(DateUtils.toLocalDateTime(resultSet.getDate(4)));
            return true;
        } catch (SQLException e) {
            Flogger.atWarning().withCause(e).log();
            return false;
        }
    }

    @Override
    public boolean buildStatement(@NonNull PreparedStatement pst) {
        try {
            pst.setInt(1, getId());
            pst.setTimestamp(2, DateUtils.toTimestamp(getApertura()));
            pst.setTimestamp(3, DateUtils.toTimestamp(getCierre()));
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
            setCierre(newValues.getCierre());
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
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("id", id)
                          .add("idCaja", idCaja)
                          .add("caja", caja.toString())
                          .add("apertura", apertura)
                          .add("cierre", cierre)
                          .toString();
    }

    @Override
    public String toStringFormatted() {
        return getId() + " " + getIdCaja();
    }
}
