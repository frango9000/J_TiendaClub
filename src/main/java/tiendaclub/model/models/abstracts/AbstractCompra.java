package tiendaclub.model.models.abstracts;

import java.time.LocalDateTime;

public abstract class AbstractCompra extends AbstractIdentifiable {
    protected int idUsuario;
    protected int idSede;
    protected int idProveedor;
    protected LocalDateTime fechahora;

    public AbstractCompra(int id, int idUsuario, int idSede, int idProveedor, LocalDateTime fechahora) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idSede = idSede;
        this.idProveedor = idProveedor;
        this.fechahora = fechahora;
    }

    public AbstractCompra(int idUsuario, int idSede, int idProveedor, LocalDateTime fechahora) {
        this.idUsuario = idUsuario;
        this.idSede = idSede;
        this.idProveedor = idProveedor;
        this.fechahora = fechahora;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdSede() {
        return idSede;
    }

    public void setIdSede(int idSede) {
        this.idSede = idSede;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
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
