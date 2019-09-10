package app.data.appdao;

import app.data.SessionDB;
import app.model.Caja;
import app.model.CierreZ;
import app.model.Usuario;
import casteldao.datasource.DataSourceIdImpl;
import casteldao.index.SetMultiMapIndexEntityImpl;
import casteldao.index.SimpleTreeMapIndexImpl;
import java.time.LocalDateTime;

public class CierreZDao extends DataSourceIdImpl<CierreZ> {

    private SetMultiMapIndexEntityImpl<Caja, CierreZ> indexCaja = new SetMultiMapIndexEntityImpl<>(getDao(), "idCaja", CierreZ::getIdCaja);
    private SimpleTreeMapIndexImpl<LocalDateTime, CierreZ> indexFechaApertura = new SimpleTreeMapIndexImpl<>(getDao(), "apertura", CierreZ::getApertura);
    private SetMultiMapIndexEntityImpl<Usuario, CierreZ> indexUsuarioApertura = new SetMultiMapIndexEntityImpl<>(getDao(), "idUsuarioApertura", CierreZ::getIdUsuarioApertura);
    private SimpleTreeMapIndexImpl<LocalDateTime, CierreZ> indexFechaCierre = new SimpleTreeMapIndexImpl<>(getDao(), "cierre", CierreZ::getCierre);
    private SetMultiMapIndexEntityImpl<Usuario, CierreZ> indexUsuarioCierre = new SetMultiMapIndexEntityImpl<>(getDao(), "idUsuarioCierre", CierreZ::getIdUsuarioCierre);

    public CierreZDao() {
        super(SessionDB.getSession(), CierreZ.TABLE_NAME, CierreZ.class);
        indexes.add(indexCaja);
        indexes.add(indexFechaApertura);
        indexes.add(indexUsuarioApertura);
        indexes.add(indexFechaCierre);
        indexes.add(indexUsuarioCierre);
    }

    public SetMultiMapIndexEntityImpl<Caja, CierreZ> getIndexCaja() {
        return indexCaja;
    }

    public SimpleTreeMapIndexImpl<LocalDateTime, CierreZ> getIndexFechaApertura() {
        return indexFechaApertura;
    }

    public SetMultiMapIndexEntityImpl<Usuario, CierreZ> getIndexUsuarioApertura() {
        return indexUsuarioApertura;
    }

    public SimpleTreeMapIndexImpl<LocalDateTime, CierreZ> getIndexFechaCierre() {
        return indexFechaCierre;
    }

    public SetMultiMapIndexEntityImpl<Usuario, CierreZ> getIndexUsuarioCierre() {
        return indexUsuarioCierre;
    }
}