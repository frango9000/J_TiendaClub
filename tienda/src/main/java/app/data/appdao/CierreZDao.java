package app.data.appdao;

import app.data.SessionDB;
import app.model.Caja;
import app.model.CierreZ;
import casteldao.datasource.DataSourceIdImpl;
import casteldao.index.SetMultiMapIndexEntityImpl;
import casteldao.index.SimpleTreeMapIndexImpl;
import java.time.LocalDateTime;

public class CierreZDao extends DataSourceIdImpl<CierreZ> {

    private SetMultiMapIndexEntityImpl<Caja, CierreZ> indexCaja = new SetMultiMapIndexEntityImpl<>(getDao(), "idCaja", CierreZ::getIdCaja);
    private SimpleTreeMapIndexImpl<LocalDateTime, CierreZ> indexFechaApertura = new SimpleTreeMapIndexImpl<>(getDao(), "fecha_in", CierreZ::getApertura);

    public CierreZDao() {
        super(SessionDB.getSession(), CierreZ.TABLE_NAME, CierreZ.class);
        indexes.add(indexCaja);
        indexes.add(indexFechaApertura);
    }

    public SetMultiMapIndexEntityImpl<Caja, CierreZ> getIndexCaja() {
        return indexCaja;
    }

    public SimpleTreeMapIndexImpl<LocalDateTime, CierreZ> getIndexFechaApertura() {
        return indexFechaApertura;
    }
}