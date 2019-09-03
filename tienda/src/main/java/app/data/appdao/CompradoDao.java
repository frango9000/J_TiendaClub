package app.data.appdao;

import app.model.Compra;
import app.model.Comprado;
import casteldao.dao.DataSourceIdImpl;
import casteldao.index.SetMultiMapIndexEntityImpl;

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
