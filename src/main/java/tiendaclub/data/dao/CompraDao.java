package tiendaclub.data.dao;

import tiendaclub.data.dao.core.IndexIdDao;
import tiendaclub.data.framework.index.MultiIndexPersistible;
import tiendaclub.model.models.Compra;
import tiendaclub.model.models.Proveedor;
import tiendaclub.model.models.Sede;
import tiendaclub.model.models.Usuario;

public class CompraDao extends IndexIdDao<Compra> {

    private MultiIndexPersistible<Usuario, Compra> indexUsuario = new MultiIndexPersistible<>(getDataSource(), "idUsuario", Compra::getIdUsuario);
    private MultiIndexPersistible<Sede, Compra> indexSede = new MultiIndexPersistible<>(getDataSource(), "idSede", Compra::getIdSede);
    private MultiIndexPersistible<Proveedor, Compra> indexProveedor = new MultiIndexPersistible<>(getDataSource(), "idProveedor", Compra::getIdProveedor);

    public CompraDao() {
        super(Compra.TABLE_NAME);
        indexes.add(indexUsuario);
        indexes.add(indexSede);
        indexes.add(indexProveedor);
    }

    public MultiIndexPersistible<Usuario, Compra> getIndexUsuario() {
        return indexUsuario;
    }

    public MultiIndexPersistible<Sede, Compra> getIndexSede() {
        return indexSede;
    }

    public MultiIndexPersistible<Proveedor, Compra> getIndexProveedor() {
        return indexProveedor;
    }
}
