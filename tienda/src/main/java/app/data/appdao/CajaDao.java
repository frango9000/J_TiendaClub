package app.data.appdao;

import app.data.casteldao.daomodel.IndexIdActiveDao;
import app.data.casteldao.index.MultiIndexPersistible;
import app.model.Caja;
import app.model.Sede;

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
