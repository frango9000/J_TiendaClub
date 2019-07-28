package app.model.abstractmodel;

import java.time.LocalDateTime;

public class AbstractCompra {
    private int id;
    private byte idUsuario;
    private byte idSede;
    private short idProveedor;
    private LocalDateTime fechahora;

    public AbstractCompra(int id, byte idUsuario, byte idSede, short idProveedor, LocalDateTime fechahora) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idSede = idSede;
        this.idProveedor = idProveedor;
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

    public byte getIdSede() {
        return idSede;
    }

    public void setIdSede(byte idSede) {
        this.idSede = idSede;
    }

    public short getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(short idProveedor) {
        this.idProveedor = idProveedor;
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

        AbstractCompra that = (AbstractCompra) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "AbstractCompra{" +
                "id=" + id +
                ", idUsuario=" + idUsuario +
                ", idSede=" + idSede +
                ", idProveedor=" + idProveedor +
                ", fechahora=" + fechahora +
                '}';
    }
}
