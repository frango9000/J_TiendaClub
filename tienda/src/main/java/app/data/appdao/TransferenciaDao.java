package app.data.appdao;

import app.model.Producto;
import app.model.Sede;
import app.model.Transferencia;
import app.model.Usuario;
import casteldao.dao.DataSourceIdImpl;
import casteldao.index.SetMultiMapIndexEntityImpl;

public class TransferenciaDao extends DataSourceIdImpl<Transferencia> {

    private SetMultiMapIndexEntityImpl<Usuario, Transferencia> indexUsuario = new SetMultiMapIndexEntityImpl<>(getDao(), "idUsuario", Transferencia::getIdUsuario);
    private SetMultiMapIndexEntityImpl<Sede, Transferencia> indexSedeOrigen = new SetMultiMapIndexEntityImpl<>(getDao(), "idSedeOrigen", Transferencia::getIdSedeOrigen);
    private SetMultiMapIndexEntityImpl<Sede, Transferencia> indexSedeDestino = new SetMultiMapIndexEntityImpl<>(getDao(), "idSedeDestino", Transferencia::getIdSedeDestino);
    private SetMultiMapIndexEntityImpl<Producto, Transferencia> indexProducto = new SetMultiMapIndexEntityImpl<>(getDao(), "idProducto", Transferencia::getIdProducto);

    public TransferenciaDao() {
        super(Transferencia.TABLE_NAME, Transferencia.class);
        indexes.add(indexUsuario);
        indexes.add(indexSedeOrigen);
        indexes.add(indexSedeDestino);
        indexes.add(indexProducto);
    }

    public SetMultiMapIndexEntityImpl<Usuario, Transferencia> getIndexUsuario() {
        return indexUsuario;
    }

    public SetMultiMapIndexEntityImpl<Sede, Transferencia> getIndexSedeOrigen() {
        return indexSedeOrigen;
    }

    public SetMultiMapIndexEntityImpl<Sede, Transferencia> getIndexSedeDestino() {
        return indexSedeDestino;
    }

    public SetMultiMapIndexEntityImpl<Producto, Transferencia> getIndexProducto() {
        return indexProducto;
    }
}
