package app.data.appdao;

import app.model.Caja;
import app.model.CierreZ;
import casteldao.dao.DataSourceIdImpl;
import casteldao.index.SetMultiMapIndexEntityImpl;

public class CierreZDao extends DataSourceIdImpl<CierreZ> {

    private SetMultiMapIndexEntityImpl<Caja, CierreZ> indexCaja = new SetMultiMapIndexEntityImpl<>(getDao(), "idCaja", CierreZ::getIdCaja);

    public CierreZDao() {
        super(CierreZ.TABLE_NAME, CierreZ.class);
        indexes.add(indexCaja);
    }

    public SetMultiMapIndexEntityImpl<Caja, CierreZ> getIndexCaja() {
        return indexCaja;
    }
}