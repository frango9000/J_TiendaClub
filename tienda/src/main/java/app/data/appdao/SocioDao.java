package app.data.appdao;

import app.data.casteldao.dao.IndexIdActiveDao;
import app.data.casteldao.index.TreeMapIndexImpl;
import app.model.Socio;
import java.time.LocalDateTime;

public class SocioDao extends IndexIdActiveDao<Socio> {

    private TreeMapIndexImpl<LocalDateTime, Socio> indexFechaIn = new TreeMapIndexImpl<>(getDao(), "fecha_in", Socio::getFechaIn);

    public SocioDao() {
        super(Socio.TABLE_NAME, Socio.class);
        indexes.add(indexFechaIn);
    }

    public TreeMapIndexImpl<LocalDateTime, Socio> getIndexFechaIn() {
        return indexFechaIn;
    }
}
