package app.data.appdao;

import app.data.casteldao.dao.DataSourceIdImpl;
import app.data.casteldao.index.SetMultiMapIndexEntityImpl;
import app.model.Caja;
import app.model.CierreZ;

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