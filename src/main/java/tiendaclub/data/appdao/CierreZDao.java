package tiendaclub.data.appdao;

import tiendaclub.data.casteldao.daomodel.IndexIdDao;
import tiendaclub.data.casteldao.index.MultiIndexPersistible;
import tiendaclub.model.Caja;
import tiendaclub.model.CierreZ;

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