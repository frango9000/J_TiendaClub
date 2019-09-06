package app.data.appdao;

import app.data.SessionDB;
import app.model.Compra;
import app.model.Comprado;
import casteldao.datasource.DataSourceIdImpl;
import casteldao.index.SetMultiMapIndexEntityImpl;

public class CompradoDao extends DataSourceIdImpl<Comprado> {

    private SetMultiMapIndexEntityImpl<Compra, Comprado> indexCompra = new SetMultiMapIndexEntityImpl<>(getDao(), "idCompra", Comprado::getIdCompra);
    //private MultiIndexPersistible<Producto, Comprado> indexProducto = new MultiIndexPersistible<>(getDataSource(), "idProducto", Comprado::getProducto);

    public CompradoDao() {
        super(SessionDB.getSession(), Comprado.TABLE_NAME, Comprado.class);
        indexes.add(indexCompra);
        //indexes.add(indexProducto);
    }

    public SetMultiMapIndexEntityImpl<Compra, Comprado> getIndexCompra() {
        return indexCompra;
    }
}
