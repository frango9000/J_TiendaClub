package app.data.appdao;

import app.data.casteldao.daomodel.IndexIdDao;
import app.data.casteldao.index.MultiIndexPersistible;
import app.data.casteldao.index.core.SimpleTreeMapIndex;
import app.model.Caja;
import app.model.Socio;
import app.model.Usuario;
import app.model.Venta;
import java.time.LocalDateTime;

public class VentaDao extends IndexIdDao<Venta> {

    private MultiIndexPersistible<Usuario, Venta> indexUsuario = new MultiIndexPersistible<>(getDataSource(), "idUsuario", Venta::getIdUsuario);
    private MultiIndexPersistible<Caja, Venta> indexCaja = new MultiIndexPersistible<>(getDataSource(), "idCaja", Venta::getIdCaja);
    private MultiIndexPersistible<Socio, Venta> indexSocio = new MultiIndexPersistible<>(getDataSource(), "idSocio", Venta::getIdSocio);
    private SimpleTreeMapIndex<LocalDateTime, Venta> indexFecha = new SimpleTreeMapIndex<>(getDataSource(), "fechahora", Venta::getFechahora);

    public VentaDao() {
        super(Venta.TABLE_NAME, Venta.class);
        indexes.add(indexUsuario);
        indexes.add(indexCaja);
        indexes.add(indexSocio);
    }

    public MultiIndexPersistible<Usuario, Venta> getIndexUsuario() {
        return indexUsuario;
    }

    public MultiIndexPersistible<Caja, Venta> getIndexCaja() {
        return indexCaja;
    }

    public MultiIndexPersistible<Socio, Venta> getIndexSocio() {
        return indexSocio;
    }

    public SimpleTreeMapIndex<LocalDateTime, Venta> getIndexFecha() {
        return indexFecha;
    }
}
