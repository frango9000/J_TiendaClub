package tiendaclub.data.framework.dao;

import java.net.Authenticator.RequestorType;
import tiendaclub.data.framework.dao.core.IndexIdDao;
import tiendaclub.data.framework.index.MultiIndexPersistible;
import tiendaclub.model.models.Caja;
import tiendaclub.model.models.CierreZ;

public class CierreZDao extends IndexIdDao<CierreZ> {

    private MultiIndexPersistible<Caja, CierreZ> indexCaja = new MultiIndexPersistible<>(getDataSource(), "idCaja", CierreZ::getIdCaja);

    public CierreZDao(String TABLE_NAME) {
        super(TABLE_NAME);
        indexes.add(indexCaja);
        RequestorType requestorType;
    }

    public MultiIndexPersistible<Caja, CierreZ> getIndexCaja() {
        return indexCaja;
    }
}