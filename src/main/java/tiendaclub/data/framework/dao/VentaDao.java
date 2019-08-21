package tiendaclub.data.framework.dao;

import tiendaclub.data.framework.dao.core.IndexIdDao;
import tiendaclub.data.framework.index.MultiIndexPersistible;
import tiendaclub.model.models.Caja;
import tiendaclub.model.models.Socio;
import tiendaclub.model.models.Usuario;
import tiendaclub.model.models.Venta;

public class VentaDao extends IndexIdDao<Venta> {

    private MultiIndexPersistible<Usuario, Venta> indexUsuario = new MultiIndexPersistible<>(getDataSource(), "idUsuario", Venta::getIdUsuario);
    private MultiIndexPersistible<Caja, Venta> indexCaja = new MultiIndexPersistible<>(getDataSource(), "idCaja", Venta::getIdCaja);
    private MultiIndexPersistible<Socio, Venta> indexSocio = new MultiIndexPersistible<>(getDataSource(), "idSocio", Venta::getIdSocio);

    public VentaDao() {
        super(Venta.TABLE_NAME);
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
}
