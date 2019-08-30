package tiendaclub.data.dao;

import tiendaclub.data.dao.core.IndexIdActiveDao;
import tiendaclub.data.framework.index.MultiIndexPersistible;
import tiendaclub.model.models.Caja;
import tiendaclub.model.models.Sede;

public class CajaDao extends IndexIdActiveDao<Caja> {

    private MultiIndexPersistible<Sede, Caja> indexSede = new MultiIndexPersistible<>(getDataSource(), "idSede", Caja::getIdSede);

    public CajaDao() {
        super(Caja.TABLE_NAME);
        indexes.add(indexSede);
    }

    public MultiIndexPersistible<Sede, Caja> getIndexSede() {
        return indexSede;
    }
}
