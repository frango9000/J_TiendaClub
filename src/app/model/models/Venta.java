package app.model.models;

import app.model.IPersistible;
import app.model.models.abstracts.AbstractVenta;

import java.time.LocalDateTime;

public class Venta extends AbstractVenta implements IPersistible {
    private Usuario usuario;
    private Caja caja;
    private Socio socio;

    public Venta(int id, byte idUsuario, byte idCaja, short idSocio, LocalDateTime fechahora) {
        super(id, idUsuario, idCaja, idSocio, fechahora);
        updateUsuario();
        updateCaja();
        updateSocio();
    }

    public Venta(byte idUsuario, byte idCaja, short idSocio, LocalDateTime fechahora) {
        super(idUsuario, idCaja, idSocio, fechahora);
        updateUsuario();
        updateCaja();
        updateSocio();
    }

    @Override
    public void setIdUsuario(byte idUsuario) {
        super.setIdUsuario(idUsuario);
        updateUsuario();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    private void updateUsuario() {

    }

    @Override
    public void setIdCaja(byte idCaja) {
        super.setIdCaja(idCaja);
        updateCaja();
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    private void updateCaja() {

    }

    @Override
    public void setIdSocio(short idSocio) {
        super.setIdSocio(idSocio);
        updateSocio();
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    private void updateSocio() {

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
