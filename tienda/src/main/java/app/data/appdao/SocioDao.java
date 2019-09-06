package app.data.appdao;

import app.data.SessionDB;
import app.model.Socio;
import casteldao.datasource.DataSourceIdActive;
import casteldao.index.SimpleTreeMapIndexImpl;
import java.time.LocalDateTime;

public class SocioDao extends DataSourceIdActive<Socio> {

    private SimpleTreeMapIndexImpl<LocalDateTime, Socio> indexFechaIn = new SimpleTreeMapIndexImpl<>(getDao(), "fecha_in", Socio::getFechaIn);

    public SocioDao() {
        super(SessionDB.getSession(), Socio.TABLE_NAME, Socio.class);
        indexes.add(indexFechaIn);
    }

    public SimpleTreeMapIndexImpl<LocalDateTime, Socio> getIndexFechaIn() {
        return indexFechaIn;
    }
}
