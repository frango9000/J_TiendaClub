package tiendaclub.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import org.checkerframework.checker.nullness.qual.NonNull;
import tiendaclub.data.DataStore;
import tiendaclub.data.appdao.CierreZDao;
import tiendaclub.data.casteldao.daomodel.IPersistible;
import tiendaclub.data.casteldao.daomodel.Persistible;
import tiendaclub.misc.DateUtils;

public class CierreZ extends Persistible {

    public static final String TABLE_NAME = "zs";
    private static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Arrays.asList("idCaja", "apertura", "cierre"));

    protected int idCaja;
    protected LocalDateTime apertura;
    protected LocalDateTime cierre;
    private Caja caja;

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

    public CierreZ(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getInt(2), DateUtils.toLocalDateTime(rs.getTimestamp(3)));
        setCierre(DateUtils.toLocalDateTime(rs.getDate(4)));
    }

    @Override
    public void buildStatement(@NonNull PreparedStatement pst) throws SQLException {
        pst.setInt(1, getId());
        pst.setTimestamp(2, DateUtils.toTimestamp(getApertura()));
        pst.setTimestamp(3, DateUtils.toTimestamp(getCierre()));
    }

    @Override
    public <V extends IPersistible> boolean restoreFrom(@NonNull V objectV) {
        if (getId() == objectV.getId() && !this.equals(objectV)) {
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
        return DataStore.getCierreZs();
    }

    public int getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
        updateCaja();
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

    private void updateCaja() {
        setCaja(DataStore.getCajas().getById().getCacheValue(getIdCaja()));
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
    public int hashCode() {
        return getId();
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
