package tiendaclub.data.dao;

import tiendaclub.data.dao.core.IndexIdDao;
import tiendaclub.data.framework.index.MultiIndexPersistible;
import tiendaclub.model.models.Vendido;
import tiendaclub.model.models.Venta;

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
