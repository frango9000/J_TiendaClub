package app.data.appdao;

import app.data.casteldao.daomodel.IndexIdDao;
import app.data.casteldao.index.MultiIndexPersistible;
import app.model.Vendido;
import app.model.Venta;

public class VendidoDao extends IndexIdDao<Vendido> {

    private MultiIndexPersistible<Venta, Vendido> indexVenta = new MultiIndexPersistible<>(getDataSource(), "idVenta", Vendido::getIdVenta);
    //private MultiIndexPersistible<Producto, Vendido> indexProducto = new MultiIndexPersistible<>(getDataSource(), "idProducto", Vendido::getIdProducto);

    public VendidoDao() {
        super(Vendido.TABLE_NAME, Vendido.class);
        indexes.add(indexVenta);
        //indexes.add(indexProducto);
    }

    public MultiIndexPersistible<Venta, Vendido> getIndexVenta() {
        return indexVenta;
    }
}
