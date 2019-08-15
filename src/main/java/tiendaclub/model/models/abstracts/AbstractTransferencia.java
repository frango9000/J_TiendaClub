package tiendaclub.model.models.abstracts;

import java.time.LocalDateTime;

public abstract class AbstractTransferencia extends Persistible {

    protected int idUsuario;
    protected int idSedeOrigen;
    protected int idSedeDestino;
    protected int idProducto;
    protected int cantidad;
    protected LocalDateTime fechahora;

    public AbstractTransferencia(int id, int idUsuario, int idSedeOrigen, int idSedeDestino, int idProducto,
            int cantidad, LocalDateTime fechahora) {
        super(id);
        this.idUsuario = idUsuario;
        this.idSedeOrigen = idSedeOrigen;
        this.idSedeDestino = idSedeDestino;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.fechahora = fechahora;
    }

    public AbstractTransferencia(int idUsuario, int idSedeOrigen, int idSedeDestino, int idProducto, int cantidad) {
        super(0);
        this.idUsuario = idUsuario;
        this.idSedeOrigen = idSedeOrigen;
        this.idSedeDestino = idSedeDestino;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdSedeOrigen() {
        return idSedeOrigen;
    }

    public void setIdSedeOrigen(int idSedeOrigen) {
        this.idSedeOrigen = idSedeOrigen;
    }

    public int getIdSedeDestino() {
        return idSedeDestino;
    }

    public void setIdSedeDestino(int idSedeDestino) {
        this.idSedeDestino = idSedeDestino;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFechahora() {
        return fechahora;
    }

    public void setFechahora(LocalDateTime fechahora) {
        this.fechahora = fechahora;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AbstractTransferencia that = (AbstractTransferencia) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "AbstractTransferencia{" +
                "id=" + id +
                ", idUsuario=" + idUsuario +
                ", idSedeOrigen=" + idSedeOrigen +
                ", idSedeDestino=" + idSedeDestino +
                ", idProducto=" + idProducto +
                ", cantidad=" + cantidad +
                ", fechahora=" + fechahora +
                '}';
    }
}
