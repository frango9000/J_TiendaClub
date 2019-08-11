package tiendaclub.model.models.abstracts;

import java.time.LocalDateTime;

public abstract class AbstractVenta extends Identifiable {
    protected int idUsuario;
    protected int idCaja;
    protected int idSocio;
    protected LocalDateTime fechahora;

    public AbstractVenta(int id, int idUsuario, int idCaja, int idSocio, LocalDateTime fechahora) {
        super(id);
        this.idUsuario = idUsuario;
        this.idCaja = idCaja;
        this.idSocio = idSocio;
        this.fechahora = fechahora;
    }

    public AbstractVenta(int idUsuario, int idCaja, int idSocio, LocalDateTime fechahora) {
        super(0);
        this.idUsuario = idUsuario;
        this.idCaja = idCaja;
        this.idSocio = idSocio;
        this.fechahora = fechahora;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
    }

    public int getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(int idSocio) {
        this.idSocio = idSocio;
    }

    public LocalDateTime getFechahora() {
        return fechahora;
    }

    public void setFechahora(LocalDateTime fechahora) {
        this.fechahora = fechahora;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractVenta that = (AbstractVenta) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "AbstractVenta{" +
                "id=" + id +
                ", idUsuario=" + idUsuario +
                ", idCaja=" + idCaja +
                ", idSocio=" + idSocio +
                ", fechahora=" + fechahora +
                '}';
    }
}
