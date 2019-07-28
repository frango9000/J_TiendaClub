package app.model.models;

import app.model.IPersistible;
import app.model.models.abstracts.AbstractVenta;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Venta extends AbstractVenta implements IPersistible {
    private Usuario usuario;
    private Caja caja;
    private Socio socio;

    private HashMap<Integer, Vendido> vendidos = new HashMap<>();

    public Venta(int id, int idUsuario, int idCaja, int idSocio, LocalDateTime fechahora) {
        super(id, idUsuario, idCaja, idSocio, fechahora);
        updateUsuario();
        updateCaja();
        updateSocio();
    }

    public Venta(int idUsuario, int idCaja, int idSocio, LocalDateTime fechahora) {
        super(idUsuario, idCaja, idSocio, fechahora);
        updateUsuario();
        updateCaja();
        updateSocio();
    }

    @Override
    public void setIdUsuario(int idUsuario) {
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
        if (usuario != null)
            usuario.getVentas().remove(id);
        //TODO DAO
        //usuario = DAO usuario . get ( idUsuario );
        usuario.getVentas().put(id, this);
    }

    @Override
    public void setIdCaja(int idCaja) {
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
        if (caja != null)
            caja.getVentas().remove(id);
        //TODO DAO
        //caja = DAO caja . get ( idCaja );
        caja.getVentas().put(id, this);
    }

    @Override
    public void setIdSocio(int idSocio) {
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
        if (socio != null)
            socio.getVentas().remove(id);
        //TODO DAO
        //socio = DAO socio . get ( idSocio );
        socio.getVentas().put(id, this);
    }

    public HashMap<Integer, Vendido> getVendidos() {
        return vendidos;
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
