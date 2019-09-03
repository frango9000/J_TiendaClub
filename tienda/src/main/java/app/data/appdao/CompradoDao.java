package app.data.appdao;

import app.data.casteldao.dao.IndexIdDataSourceImpl;
import app.data.casteldao.index.SetMultiMapIndexImpl;
import app.model.Compra;
import app.model.Comprado;

public class CompradoDao extends IndexIdDataSourceImpl<Comprado> {

    private SetMultiMapIndexImpl<Compra, Comprado> indexCompra = new SetMultiMapIndexImpl<>(getDao(), "idCompra", Comprado::getCompra);
    //private MultiIndexPersistible<Producto, Comprado> indexProducto = new MultiIndexPersistible<>(getDataSource(), "idProducto", Comprado::getProducto);

    public CompradoDao() {
        super(Comprado.TABLE_NAME, Comprado.class);
        indexes.add(indexCompra);
        //indexes.add(indexProducto);
    }

    public SetMultiMapIndexImpl<Compra, Comprado> getIndexCompra() {
        return indexCompra;
    }
}
