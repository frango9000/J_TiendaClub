package app.data.appdao;

import app.data.casteldao.dao.IndexIdDataSourceImpl;
import app.data.casteldao.index.SetMultiMapIndexImpl;
import app.model.Producto;
import app.model.Sede;
import app.model.Transferencia;
import app.model.Usuario;

public class TransferenciaDao extends IndexIdDataSourceImpl<Transferencia> {

    private SetMultiMapIndexImpl<Usuario, Transferencia> indexUsuario = new SetMultiMapIndexImpl<>(getDao(), "idUsuario", Transferencia::getUsuario);
    private SetMultiMapIndexImpl<Sede, Transferencia> indexSedeOrigen = new SetMultiMapIndexImpl<>(getDao(), "idSedeOrigen", Transferencia::getSedeOrigen);
    private SetMultiMapIndexImpl<Sede, Transferencia> indexSedeDestino = new SetMultiMapIndexImpl<>(getDao(), "idSedeDestino", Transferencia::getSedeDestino);
    private SetMultiMapIndexImpl<Producto, Transferencia> indexProducto = new SetMultiMapIndexImpl<>(getDao(), "idProducto", Transferencia::getProducto);

    public TransferenciaDao() {
        super(Transferencia.TABLE_NAME, Transferencia.class);
        indexes.add(indexUsuario);
        indexes.add(indexSedeOrigen);
        indexes.add(indexSedeDestino);
        indexes.add(indexProducto);
    }

    public SetMultiMapIndexImpl<Usuario, Transferencia> getIndexUsuario() {
        return indexUsuario;
    }

    public SetMultiMapIndexImpl<Sede, Transferencia> getIndexSedeOrigen() {
        return indexSedeOrigen;
    }

    public SetMultiMapIndexImpl<Sede, Transferencia> getIndexSedeDestino() {
        return indexSedeDestino;
    }

    public SetMultiMapIndexImpl<Producto, Transferencia> getIndexProducto() {
        return indexProducto;
    }
}
