package app.data.appdao;

import app.data.casteldao.dao.IndexIdDataSourceImpl;
import app.data.casteldao.index.SetMultiMapIndexImpl;
import app.model.Caja;
import app.model.CierreZ;

public class CierreZDao extends IndexIdDataSourceImpl<CierreZ> {

    private SetMultiMapIndexImpl<Caja, CierreZ> indexCaja = new SetMultiMapIndexImpl<>(getDao(), "idCaja", CierreZ::getCaja);

    public CierreZDao() {
        super(CierreZ.TABLE_NAME, CierreZ.class);
        indexes.add(indexCaja);
    }

    public SetMultiMapIndexImpl<Caja, CierreZ> getIndexCaja() {
        return indexCaja;
    }
}