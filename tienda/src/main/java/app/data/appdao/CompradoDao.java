package app.data.appdao;

import app.data.casteldao.daomodel.IndexIdDao;
import app.data.casteldao.index.MultiIndexPersistible;
import app.model.Compra;
import app.model.Comprado;

public class CompradoDao extends IndexIdDao<Comprado> {

    private MultiIndexPersistible<Compra, Comprado> indexCompra = new MultiIndexPersistible<>(getDataSource(), "idCompra", Comprado::getIdCompra);
    //private MultiIndexPersistible<Producto, Comprado> indexProducto = new MultiIndexPersistible<>(getDataSource(), "idProducto", Comprado::getIdProducto);

    public CompradoDao() {
        super(Comprado.TABLE_NAME, Comprado.class);
        indexes.add(indexCompra);
        //indexes.add(indexProducto);
    }

    public MultiIndexPersistible<Compra, Comprado> getIndexCompra() {
        return indexCompra;
    }
}
