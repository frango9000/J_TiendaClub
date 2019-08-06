package tiendaclub.model.models.abstracts;

public abstract class AbstractComprado extends Persistible {
    protected int idCompra;
    protected int idProducto;
    protected int cantidad;
    protected int precioUnidad;

    public AbstractComprado(int id, int idCompra, int idProducto, int cantidad, int precioUnidad) {
        this.id = id;
        this.idCompra = idCompra;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioUnidad = precioUnidad;
    }

    public AbstractComprado(int idCompra, int idProducto, int cantidad, int precioUnidad) {
        this.idCompra = idCompra;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioUnidad = precioUnidad;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
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

        AbstractComprado that = (AbstractComprado) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "AbstractComprados{" +
                "id=" + id +
                ", idCompra=" + idCompra +
                ", idProducto=" + idProducto +
                ", cantidad=" + cantidad +
                ", precioUnidad=" + precioUnidad +
                '}';
    }
}
