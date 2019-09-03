package app.data.appdao;

import app.data.casteldao.dao.DataSourceIdImpl;
import app.data.casteldao.index.SetMultiMapIndexEntityImpl;
import app.model.Compra;
import app.model.Proveedor;
import app.model.Sede;
import app.model.Usuario;

public class CompraDao extends DataSourceIdImpl<Compra> {

    private SetMultiMapIndexEntityImpl<Usuario, Compra> indexUsuario = new SetMultiMapIndexEntityImpl<>(getDao(), "idUsuario", Compra::getIdUsuario);
    private SetMultiMapIndexEntityImpl<Sede, Compra> indexSede = new SetMultiMapIndexEntityImpl<>(getDao(), "idSede", Compra::getIdSede);
    private SetMultiMapIndexEntityImpl<Proveedor, Compra> indexProveedor = new SetMultiMapIndexEntityImpl<>(getDao(), "idProveedor", Compra::getIdProveedor);

    public CompraDao() {
        super(Compra.TABLE_NAME, Compra.class);
        indexes.add(indexUsuario);
        indexes.add(indexSede);
        indexes.add(indexProveedor);
    }

    public SetMultiMapIndexEntityImpl<Usuario, Compra> getIndexUsuario() {
        return indexUsuario;
    }

    public SetMultiMapIndexEntityImpl<Sede, Compra> getIndexSede() {
        return indexSede;
    }

    public SetMultiMapIndexEntityImpl<Proveedor, Compra> getIndexProveedor() {
        return indexProveedor;
    }
}
