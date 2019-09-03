package app.data.appdao;

import app.data.casteldao.dao.IndexIdDataSourceImpl;
import app.data.casteldao.index.SetMultiMapIndexImpl;
import app.model.Compra;
import app.model.Proveedor;
import app.model.Sede;
import app.model.Usuario;

public class CompraDao extends IndexIdDataSourceImpl<Compra> {

    private SetMultiMapIndexImpl<Usuario, Compra> indexUsuario = new SetMultiMapIndexImpl<>(getDao(), "idUsuario", Compra::getUsuario);
    private SetMultiMapIndexImpl<Sede, Compra> indexSede = new SetMultiMapIndexImpl<>(getDao(), "idSede", Compra::getSede);
    private SetMultiMapIndexImpl<Proveedor, Compra> indexProveedor = new SetMultiMapIndexImpl<>(getDao(), "idProveedor", Compra::getProveedor);

    public CompraDao() {
        super(Compra.TABLE_NAME, Compra.class);
        indexes.add(indexUsuario);
        indexes.add(indexSede);
        indexes.add(indexProveedor);
    }

    public SetMultiMapIndexImpl<Usuario, Compra> getIndexUsuario() {
        return indexUsuario;
    }

    public SetMultiMapIndexImpl<Sede, Compra> getIndexSede() {
        return indexSede;
    }

    public SetMultiMapIndexImpl<Proveedor, Compra> getIndexProveedor() {
        return indexProveedor;
    }
}
