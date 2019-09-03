package app.data.appdao;

import app.data.casteldao.dao.DataSourceIdImpl;
import app.data.casteldao.index.SetMultiMapIndexEntityImpl;
import app.data.casteldao.index.SimpleTreeMapIndexImpl;
import app.model.Caja;
import app.model.Socio;
import app.model.Usuario;
import app.model.Venta;
import java.time.LocalDateTime;

public class VentaDao extends DataSourceIdImpl<Venta> {

    private SetMultiMapIndexEntityImpl<Usuario, Venta> indexUsuario = new SetMultiMapIndexEntityImpl<>(getDao(), "idUsuario", Venta::getIdUsuario);
    private SetMultiMapIndexEntityImpl<Caja, Venta> indexCaja = new SetMultiMapIndexEntityImpl<>(getDao(), "idCaja", Venta::getIdCaja);
    private SetMultiMapIndexEntityImpl<Socio, Venta> indexSocio = new SetMultiMapIndexEntityImpl<>(getDao(), "idSocio", Venta::getIdSocio);
    private SimpleTreeMapIndexImpl<LocalDateTime, Venta> indexFecha = new SimpleTreeMapIndexImpl<>(getDao(), "fechahora", Venta::getFechahora);

    public VentaDao() {
        super(Venta.TABLE_NAME, Venta.class);
        indexes.add(indexUsuario);
        indexes.add(indexCaja);
        indexes.add(indexSocio);
    }

    public SetMultiMapIndexEntityImpl<Usuario, Venta> getIndexUsuario() {
        return indexUsuario;
    }

    public SetMultiMapIndexEntityImpl<Caja, Venta> getIndexCaja() {
        return indexCaja;
    }

    public SetMultiMapIndexEntityImpl<Socio, Venta> getIndexSocio() {
        return indexSocio;
    }

    public SimpleTreeMapIndexImpl<LocalDateTime, Venta> getIndexFecha() {
        return indexFecha;
    }
}
