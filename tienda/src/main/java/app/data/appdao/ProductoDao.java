package app.data.appdao;

import app.data.casteldao.daomodel.IndexIdActiveDao;
import app.data.casteldao.index.MultiIndexPersistible;
import app.model.Categoria;
import app.model.Producto;

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