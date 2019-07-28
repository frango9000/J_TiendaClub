package app.model.models;

import app.model.IPersistible;
import app.model.models.abstracts.AbstractVenta;

import java.time.LocalDateTime;

public class Venta extends AbstractVenta implements IPersistible {
    public Venta(int id, byte idUsuario, byte idCaja, short idSocio, LocalDateTime fechahora) {
        super(id, idUsuario, idCaja, idSocio, fechahora);
    }

    public Venta(byte idUsuario, byte idCaja, short idSocio, LocalDateTime fechahora) {
        super(idUsuario, idCaja, idSocio, fechahora);
    }

    @Override
    public int updateOnDb() {
        return 0;
    }

    @Override
    public int refreshFromDb() {
        return 0;
    }
}
