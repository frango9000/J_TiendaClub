package app.data.appdao;

import app.data.casteldao.dao.DataSourceIdActive;
import app.data.casteldao.index.SetMultiMapIndexEntityImpl;
import app.model.Caja;
import app.model.Sede;

public class CajaDao extends DataSourceIdActive<Caja> {

    private SetMultiMapIndexEntityImpl<Sede, Caja> indexSede = new SetMultiMapIndexEntityImpl<Sede, Caja>(getDao(), "idSede", Caja::getIdSede);

    public CajaDao() {
        super(Caja.TABLE_NAME, Caja.class);
        indexes.add(indexSede);
    }

    public SetMultiMapIndexEntityImpl<Sede, Caja> getIndexSede() {
        return indexSede;
    }
}
