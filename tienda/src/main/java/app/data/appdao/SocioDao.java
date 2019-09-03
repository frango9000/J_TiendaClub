package app.data.appdao;

import app.data.casteldao.dao.DataSourceIdActive;
import app.data.casteldao.index.SimpleTreeMapIndexImpl;
import app.model.Socio;
import java.time.LocalDateTime;

public class SocioDao extends DataSourceIdActive<Socio> {

    private SimpleTreeMapIndexImpl<LocalDateTime, Socio> indexFechaIn = new SimpleTreeMapIndexImpl<>(getDao(), "fecha_in", Socio::getFechaIn);

    public SocioDao() {
        super(Socio.TABLE_NAME, Socio.class);
        indexes.add(indexFechaIn);
    }

    public SimpleTreeMapIndexImpl<LocalDateTime, Socio> getIndexFechaIn() {
        return indexFechaIn;
    }
}
