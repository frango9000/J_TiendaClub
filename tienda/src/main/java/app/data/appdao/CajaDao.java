package app.data.appdao;

import app.model.Caja;
import app.model.Sede;
import casteldao.dao.DataSourceIdActive;
import casteldao.index.SetMultiMapIndexEntityImpl;

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
