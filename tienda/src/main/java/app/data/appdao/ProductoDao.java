package app.data.appdao;

import app.data.SessionDB;
import app.model.Categoria;
import app.model.Producto;
import casteldao.datasource.DataSourceIdActive;
import casteldao.index.SetMultiMapIndexEntityImpl;

public class ProductoDao extends DataSourceIdActive<Producto> {

    private SetMultiMapIndexEntityImpl<Categoria, Producto> indexCategoria = new SetMultiMapIndexEntityImpl<>(getDao(), "idCategoria", Producto::getIdCategoria);

    public ProductoDao() {
        super(SessionDB.getSession(), Producto.TABLE_NAME, Producto.class);
        indexes.add(indexCategoria);
    }

    public SetMultiMapIndexEntityImpl<Categoria, Producto> getIndexCategoria() {
        return indexCategoria;
    }
}