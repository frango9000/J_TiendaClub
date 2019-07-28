package app.model.models.abstracts;

import java.time.LocalDateTime;

public abstract class AbstractVenta {
    protected int id;
    protected byte idUsuario;
    protected byte idCaja;
    protected short idSocio;
    protected LocalDateTime fechahora;

    public AbstractVenta(int id, byte idUsuario, byte idCaja, short idSocio, LocalDateTime fechahora) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idCaja = idCaja;
        this.idSocio = idSocio;
        this.fechahora = fechahora;
    }

    public AbstractVenta(byte idUsuario, byte idCaja, short idSocio, LocalDateTime fechahora) {
        this.idUsuario = idUsuario;
        this.idCaja = idCaja;
        this.idSocio = idSocio;
        this.fechahora = fechahora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(byte idUsuario) {
        this.idUsuario = idUsuario;
    }

    public byte getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(byte idCaja) {
        this.idCaja = idCaja;
    }

    public short getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(short idSocio) {
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
