package tiendaclub.data.appdao;

import java.time.LocalDateTime;
import tiendaclub.data.casteldao.daomodel.IndexIdActiveDao;
import tiendaclub.data.casteldao.index.core.SimpleTreeMapIndex;
import tiendaclub.model.Socio;

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
