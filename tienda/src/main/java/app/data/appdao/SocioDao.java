package app.data.appdao;

import app.data.casteldao.daomodel.IndexIdActiveDao;
import app.data.casteldao.index.core.SimpleTreeMapIndex;
import app.model.Socio;
import java.time.LocalDateTime;

public class SocioDao extends IndexIdActiveDao<Socio> {

    private SimpleTreeMapIndex<LocalDateTime, Socio> indexFechaIn = new SimpleTreeMapIndex<LocalDateTime, Socio>(getDataSource(), "fecha_in", Socio::getFechaIn);

    public SocioDao() {
        super(Socio.TABLE_NAME, Socio.class);
        indexes.add(indexFechaIn);
    }

    public SimpleTreeMapIndex<LocalDateTime, Socio> getIndexFechaIn() {
        return indexFechaIn;
    }
}
