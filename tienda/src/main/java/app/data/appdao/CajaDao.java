package app.data.appdao;

import app.data.SessionDB;
import app.model.Caja;
import app.model.Sede;
import casteldao.datasource.DataSourceIdActive;
import casteldao.index.SetMultiMapIndexEntityImpl;

public class CajaDao extends DataSourceIdActive<Caja> {

    private SetMultiMapIndexEntityImpl<Sede, Caja> indexSede = new SetMultiMapIndexEntityImpl<Sede, Caja>(getDao(), "idSede", Caja::getIdSede);

    public CajaDao() {
        super(SessionDB.getSession(), Caja.TABLE_NAME, Caja.class);
        indexes.add(indexSede);
    }

    public SetMultiMapIndexEntityImpl<Sede, Caja> getIndexSede() {
        return indexSede;
    }
}
