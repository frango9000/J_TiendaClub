package app.data.appdao;

import app.data.casteldao.dao.DataSourceIdActive;
import app.data.casteldao.index.SetMultiMapIndexEntityImpl;
import app.model.Categoria;
import app.model.Producto;

public class ProductoDao extends DataSourceIdActive<Producto> {

    private SetMultiMapIndexEntityImpl<Categoria, Producto> indexCategoria = new SetMultiMapIndexEntityImpl<>(getDao(), "idCategoria", Producto::getIdCategoria);

    public ProductoDao() {
        super(Producto.TABLE_NAME, Producto.class);
        indexes.add(indexCategoria);
    }

    public SetMultiMapIndexEntityImpl<Categoria, Producto> getIndexCategoria() {
        return indexCategoria;
    }
}