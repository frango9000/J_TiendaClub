package tiendaclub.data.appdao;

import tiendaclub.data.casteldao.daomodel.IndexIdDao;
import tiendaclub.data.casteldao.index.MultiIndexPersistible;
import tiendaclub.model.Compra;
import tiendaclub.model.Proveedor;
import tiendaclub.model.Sede;
import tiendaclub.model.Usuario;

public class CompraDao extends IndexIdDao<Compra> {

    private MultiIndexPersistible<Usuario, Compra> indexUsuario = new MultiIndexPersistible<>(getDataSource(), "idUsuario", Compra::getIdUsuario);
    private MultiIndexPersistible<Sede, Compra> indexSede = new MultiIndexPersistible<>(getDataSource(), "idSede", Compra::getIdSede);
    private MultiIndexPersistible<Proveedor, Compra> indexProveedor = new MultiIndexPersistible<>(getDataSource(), "idProveedor", Compra::getIdProveedor);

    public CompraDao() {
        super(Compra.TABLE_NAME, Compra.class);
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
