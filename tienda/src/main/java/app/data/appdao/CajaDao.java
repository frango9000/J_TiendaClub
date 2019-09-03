package app.data.appdao;

import app.data.casteldao.dao.IndexIdActiveDao;
import app.data.casteldao.index.SetMultiMapIndexImpl;
import app.model.Caja;
import app.model.Sede;

public class CajaDao extends IndexIdActiveDao<Caja> {

    private SetMultiMapIndexImpl<Sede, Caja> indexSede = new SetMultiMapIndexImpl<>(getDao(), "idSede", Caja::getSede);

    public CajaDao() {
        super(Caja.TABLE_NAME, Caja.class);
        indexes.add(indexSede);
    }

    public SetMultiMapIndexImpl<Sede, Caja> getIndexSede() {
        return indexSede;
    }
}
