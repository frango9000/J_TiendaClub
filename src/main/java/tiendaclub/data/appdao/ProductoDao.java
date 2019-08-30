package tiendaclub.data.appdao;

import tiendaclub.data.casteldao.daomodel.IndexIdActiveDao;
import tiendaclub.data.casteldao.index.MultiIndexPersistible;
import tiendaclub.model.Categoria;
import tiendaclub.model.Producto;

public class ProductoDao extends IndexIdActiveDao<Producto> {

    private MultiIndexPersistible<Categoria, Producto> indexCategoria = new MultiIndexPersistible<>(getDataSource(), "idCategoria", Producto::getIdCategoria);

    public ProductoDao() {
        super(Producto.TABLE_NAME, Producto.class);
        indexes.add(indexCategoria);
    }

    public MultiIndexPersistible<Categoria, Producto> getIndexCategoria() {
        return indexCategoria;
    }
}