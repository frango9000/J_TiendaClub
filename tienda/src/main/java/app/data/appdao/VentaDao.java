package app.data.appdao;

import app.data.SessionDB;
import app.model.Caja;
import app.model.Socio;
import app.model.Usuario;
import app.model.Venta;
import casteldao.datasource.DataSourceIdImpl;
import casteldao.index.SetMultiMapIndexEntityImpl;
import casteldao.index.SimpleTreeMapIndexImpl;
import java.time.LocalDateTime;

public class VentaDao extends DataSourceIdImpl<Venta> {

    private SetMultiMapIndexEntityImpl<Usuario, Venta> indexUsuario = new SetMultiMapIndexEntityImpl<>(getDao(), "idUsuario", Venta::getIdUsuario);
    private SetMultiMapIndexEntityImpl<Caja, Venta> indexCaja = new SetMultiMapIndexEntityImpl<>(getDao(), "idCaja", Venta::getIdCaja);
    private SetMultiMapIndexEntityImpl<Socio, Venta> indexSocio = new SetMultiMapIndexEntityImpl<>(getDao(), "idSocio", Venta::getIdSocio);
    private SimpleTreeMapIndexImpl<LocalDateTime, Venta> indexFecha = new SimpleTreeMapIndexImpl<>(getDao(), "fechahora", Venta::getFechahora);

    public VentaDao() {
        super(SessionDB.getSession(), Venta.TABLE_NAME, Venta.class);
        indexes.add(indexUsuario);
        indexes.add(indexCaja);
        indexes.add(indexSocio);
        indexes.add(indexFecha);
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
