package tiendaclub.model.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import tiendaclub.data.DataStore;
import tiendaclub.model.models.abstracts.AbstractCierreZ;
import tiendaclub.model.utils.DateUtils;

public class CierreZ extends AbstractCierreZ {

    public static final String TABLE_NAME = "zs";
    private static final ArrayList<String> COL_NAMES = new ArrayList<>(Arrays.asList("idCaja", "apertura", "cierre"));

    private Caja caja;

    public CierreZ(int id, int idCaja, LocalDateTime apertura) {
        super(id, idCaja, apertura);
        updateCaja();
    }

    public CierreZ(int idCaja, LocalDateTime apertura) {
        super(idCaja, apertura);
        updateCaja();
    }

    public CierreZ(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getInt(2), DateUtils.toLocalDateTime(rs.getTimestamp(3)));
        cierre = DateUtils.toLocalDateTime(rs.getDate(4));
    }

    @Override
    public void buildStatement(PreparedStatement pst) throws SQLException {
        pst.setInt(1, getId());
        pst.setTimestamp(2, DateUtils.toTimestamp(getApertura()));
        pst.setTimestamp(3, DateUtils.toTimestamp(getCierre()));
    }

    @Override
    public void updateObject(ResultSet rs) throws SQLException {
        //setId(rs.getInt(1));
        setIdCaja(rs.getInt(2));
        setApertura(DateUtils.toLocalDateTime(rs.getTimestamp(3)));
        setCierre(DateUtils.toLocalDateTime(rs.getTimestamp(4)));
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
        if (caja != null) {
            caja.getCierresZs().remove(getId());
        }
        this.caja = caja2;
        if (caja != null) {
            caja.getCierresZs().putIfAbsent(getId(), this);
        }
    }

    private void updateCaja() {
        setCaja(DataStore.getCajas().getIdIndex().getCacheValue(getIdCaja()));
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
