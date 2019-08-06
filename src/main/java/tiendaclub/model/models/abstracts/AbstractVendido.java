package tiendaclub.model.models.abstracts;

public abstract class AbstractVendido extends Persistible {
    protected int idVenta;
    protected int idProducto;
    protected int cantidad;
    protected int precioUnidad;

    public AbstractVendido(int id, int idVenta, int idProducto, int cantidad, int precioUnidad) {
        this.id = id;
        this.idVenta = idVenta;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioUnidad = precioUnidad;
    }

    public AbstractVendido(int idVenta, int idProducto, int cantidad, int precioUnidad) {
        this.idVenta = idVenta;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioUnidad = precioUnidad;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
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

    public int getPrecioUnidad() {
        return precioUnidad;
    }

    public void setPrecioUnidad(int precioUnidad) {
        this.precioUnidad = precioUnidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractVendido that = (AbstractVendido) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "AbstractVendidos{" +
                "id=" + id +
                ", idVenta=" + idVenta +
                ", idProducto=" + idProducto +
                ", cantidad=" + cantidad +
                ", precioUnidad=" + precioUnidad +
                '}';
    }
}
