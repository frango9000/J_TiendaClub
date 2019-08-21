package tiendaclub.data.framework.dao;

import tiendaclub.data.framework.dao.core.IndexIdActiveDao;
import tiendaclub.data.framework.index.MultiIndexPersistible;
import tiendaclub.model.models.Categoria;
import tiendaclub.model.models.Producto;

public class ProductoDao extends IndexIdActiveDao<Producto> {

    private MultiIndexPersistible<Categoria, Producto> indexCategoria = new MultiIndexPersistible<>(getDataSource(), "idCategoria", Producto::getIdCategoria);

    public ProductoDao() {
        super(Producto.TABLE_NAME);
        indexes.add(indexCategoria);
    }

    public MultiIndexPersistible<Categoria, Producto> getIndexCategoria() {
        return indexCategoria;
    }
}