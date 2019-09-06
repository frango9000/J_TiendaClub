package app.data.appdao;

import app.data.SessionDB;
import app.model.Vendido;
import app.model.Venta;
import casteldao.datasource.DataSourceIdImpl;
import casteldao.index.SetMultiMapIndexEntityImpl;

public class VendidoDao extends DataSourceIdImpl<Vendido> {

    private SetMultiMapIndexEntityImpl<Venta, Vendido> indexVenta = new SetMultiMapIndexEntityImpl<>(getDao(), "idVenta", Vendido::getIdVenta);
    //private MultiIndexPersistible<Producto, Vendido> indexProducto = new MultiIndexPersistible<>(getDataSource(), "idProducto", Vendido::getIdProducto);

    public VendidoDao() {
        super(SessionDB.getSession(), Vendido.TABLE_NAME, Vendido.class);
        indexes.add(indexVenta);
        //indexes.add(indexProducto);
    }

    public SetMultiMapIndexEntityImpl<Venta, Vendido> getIndexVenta() {
        return indexVenta;
    }
}
