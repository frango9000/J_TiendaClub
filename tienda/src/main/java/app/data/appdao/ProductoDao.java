package app.data.appdao;

import app.data.casteldao.dao.IndexIdActiveDao;
import app.data.casteldao.index.SetMultiMapIndexImpl;
import app.model.Categoria;
import app.model.Producto;

public class ProductoDao extends IndexIdActiveDao<Producto> {

    private SetMultiMapIndexImpl<Categoria, Producto> indexCategoria = new SetMultiMapIndexImpl<>(getDao(), "idCategoria", Producto::getCategoria);

    public ProductoDao() {
        super(Producto.TABLE_NAME, Producto.class);
        indexes.add(indexCategoria);
    }

    public SetMultiMapIndexImpl<Categoria, Producto> getIndexCategoria() {
        return indexCategoria;
    }
}