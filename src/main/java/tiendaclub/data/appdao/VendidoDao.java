package tiendaclub.data.appdao;

import tiendaclub.data.casteldao.daomodel.IndexIdDao;
import tiendaclub.data.casteldao.index.MultiIndexPersistible;
import tiendaclub.model.Vendido;
import tiendaclub.model.Venta;

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
