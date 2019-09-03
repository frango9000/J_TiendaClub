package app.data.appdao;

import app.data.casteldao.dao.DataSourceIdImpl;
import app.data.casteldao.index.SetMultiMapIndexEntityImpl;
import app.model.Compra;
import app.model.Comprado;

public class CompradoDao extends DataSourceIdImpl<Comprado> {

    private SetMultiMapIndexEntityImpl<Compra, Comprado> indexCompra = new SetMultiMapIndexEntityImpl<>(getDao(), "idCompra", Comprado::getIdCompra);
    //private MultiIndexPersistible<Producto, Comprado> indexProducto = new MultiIndexPersistible<>(getDataSource(), "idProducto", Comprado::getProducto);

    public CompradoDao() {
        super(Comprado.TABLE_NAME, Comprado.class);
        indexes.add(indexCompra);
        //indexes.add(indexProducto);
    }

    public SetMultiMapIndexEntityImpl<Compra, Comprado> getIndexCompra() {
        return indexCompra;
    }
}
