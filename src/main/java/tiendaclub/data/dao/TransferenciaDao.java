package tiendaclub.data.dao;

import tiendaclub.data.dao.core.IndexIdDao;
import tiendaclub.data.framework.index.MultiIndexPersistible;
import tiendaclub.model.models.Producto;
import tiendaclub.model.models.Sede;
import tiendaclub.model.models.Transferencia;
import tiendaclub.model.models.Usuario;

public class TransferenciaDao extends IndexIdDao<Transferencia> {

    private MultiIndexPersistible<Usuario, Transferencia> indexUsuario = new MultiIndexPersistible<>(getDataSource(), "idUsuario", Transferencia::getIdUsuario);
    private MultiIndexPersistible<Sede, Transferencia> indexSedeOrigen = new MultiIndexPersistible<>(getDataSource(), "idSedeOrigen", Transferencia::getIdSedeOrigen);
    private MultiIndexPersistible<Sede, Transferencia> indexSedeDestino = new MultiIndexPersistible<>(getDataSource(), "idSedeDestino", Transferencia::getIdSedeDestino);
    private MultiIndexPersistible<Producto, Transferencia> indexProducto = new MultiIndexPersistible<>(getDataSource(), "idProducto", Transferencia::getIdProducto);

    public TransferenciaDao() {
        super(Transferencia.TABLE_NAME);
        indexes.add(indexUsuario);
        indexes.add(indexSedeOrigen);
        indexes.add(indexSedeDestino);
        indexes.add(indexProducto);
    }

    public MultiIndexPersistible<Usuario, Transferencia> getIndexUsuario() {
        return indexUsuario;
    }

    public MultiIndexPersistible<Sede, Transferencia> getIndexSedeOrigen() {
        return indexSedeOrigen;
    }

    public MultiIndexPersistible<Sede, Transferencia> getIndexSedeDestino() {
        return indexSedeDestino;
    }

    public MultiIndexPersistible<Producto, Transferencia> getIndexProducto() {
        return indexProducto;
    }
}
