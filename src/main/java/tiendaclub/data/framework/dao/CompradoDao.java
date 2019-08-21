package tiendaclub.data.framework.dao;

import tiendaclub.data.framework.dao.core.IndexIdDao;
import tiendaclub.data.framework.index.MultiIndexPersistible;
import tiendaclub.model.models.Compra;
import tiendaclub.model.models.Comprado;

public class CompradoDao extends IndexIdDao<Comprado> {

    private MultiIndexPersistible<Compra, Comprado> indexCompra = new MultiIndexPersistible<>(getDataSource(), "idCompra", Comprado::getIdCompra);
    //private MultiIndexPersistible<Producto, Comprado> indexProducto = new MultiIndexPersistible<>(getDataSource(), "idProducto", Comprado::getIdProducto);

    public CompradoDao() {
        super(Comprado.TABLE_NAME);
        indexes.add(indexCompra);
        //indexes.add(indexProducto);
    }

    public MultiIndexPersistible<Compra, Comprado> getIndexCompra() {
        return indexCompra;
    }
}
