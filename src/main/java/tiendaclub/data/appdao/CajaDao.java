package tiendaclub.data.appdao;

import tiendaclub.data.casteldao.daomodel.IndexIdActiveDao;
import tiendaclub.data.casteldao.index.MultiIndexPersistible;
import tiendaclub.model.Caja;
import tiendaclub.model.Sede;

public class CajaDao extends IndexIdActiveDao<Caja> {

    private MultiIndexPersistible<Sede, Caja> indexSede = new MultiIndexPersistible<>(getDataSource(), "idSede", Caja::getIdSede);

    public CajaDao() {
        super(Caja.TABLE_NAME, Caja.class);
        indexes.add(indexSede);
    }

    public MultiIndexPersistible<Sede, Caja> getIndexSede() {
        return indexSede;
    }
}
