package tiendaclub.data.dao;

import tiendaclub.data.dao.core.IndexIdDao;
import tiendaclub.data.framework.index.MultiIndexPersistible;
import tiendaclub.model.models.Caja;
import tiendaclub.model.models.CierreZ;

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