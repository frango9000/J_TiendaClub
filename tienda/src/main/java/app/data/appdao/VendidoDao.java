package app.data.appdao;

import app.data.casteldao.dao.IndexIdDataSourceImpl;
import app.data.casteldao.index.SetMultiMapIndexImpl;
import app.model.Vendido;
import app.model.Venta;

public class VendidoDao extends IndexIdDataSourceImpl<Vendido> {

    private SetMultiMapIndexImpl<Venta, Vendido> indexVenta = new SetMultiMapIndexImpl<>(getDao(), "idVenta", Vendido::getVenta);
    //private MultiIndexPersistible<Producto, Vendido> indexProducto = new MultiIndexPersistible<>(getDataSource(), "idProducto", Vendido::getIdProducto);

    public VendidoDao() {
        super(Vendido.TABLE_NAME, Vendido.class);
        indexes.add(indexVenta);
        //indexes.add(indexProducto);
    }

    public SetMultiMapIndexImpl<Venta, Vendido> getIndexVenta() {
        return indexVenta;
    }
}
