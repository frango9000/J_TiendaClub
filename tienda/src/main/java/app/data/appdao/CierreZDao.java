package app.data.appdao;

import app.data.casteldao.daomodel.IndexIdDao;
import app.data.casteldao.index.MultiIndexPersistible;
import app.model.Caja;
import app.model.CierreZ;

public class CierreZDao extends IndexIdDao<CierreZ> {

    private MultiIndexPersistible<Caja, CierreZ> indexCaja = new MultiIndexPersistible<>(getDataSource(), "idCaja", CierreZ::getIdCaja);

    public CierreZDao() {
        super(CierreZ.TABLE_NAME, CierreZ.class);
        indexes.add(indexCaja);
    }

    public MultiIndexPersistible<Caja, CierreZ> getIndexCaja() {
        return indexCaja;
    }
}