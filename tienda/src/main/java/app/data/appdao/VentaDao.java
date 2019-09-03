package app.data.appdao;

import app.data.casteldao.dao.IndexIdDataSourceImpl;
import app.data.casteldao.index.SetMultiMapIndexImpl;
import app.data.casteldao.index.TreeMapIndexImpl;
import app.model.Caja;
import app.model.Socio;
import app.model.Usuario;
import app.model.Venta;
import java.time.LocalDateTime;

public class VentaDao extends IndexIdDataSourceImpl<Venta> {

    private SetMultiMapIndexImpl<Usuario, Venta> indexUsuario = new SetMultiMapIndexImpl<>(getDao(), "idUsuario", Venta::getUsuario);
    private SetMultiMapIndexImpl<Caja, Venta> indexCaja = new SetMultiMapIndexImpl<>(getDao(), "idCaja", Venta::getCaja);
    private SetMultiMapIndexImpl<Socio, Venta> indexSocio = new SetMultiMapIndexImpl<>(getDao(), "idSocio", Venta::getSocio);
    private TreeMapIndexImpl<LocalDateTime, Venta> indexFecha = new TreeMapIndexImpl<>(getDao(), "fechahora", Venta::getFechahora);

    public VentaDao() {
        super(Venta.TABLE_NAME, Venta.class);
        indexes.add(indexUsuario);
        indexes.add(indexCaja);
        indexes.add(indexSocio);
    }

    public SetMultiMapIndexImpl<Usuario, Venta> getIndexUsuario() {
        return indexUsuario;
    }

    public SetMultiMapIndexImpl<Caja, Venta> getIndexCaja() {
        return indexCaja;
    }

    public SetMultiMapIndexImpl<Socio, Venta> getIndexSocio() {
        return indexSocio;
    }

    public TreeMapIndexImpl<LocalDateTime, Venta> getIndexFecha() {
        return indexFecha;
    }
}
