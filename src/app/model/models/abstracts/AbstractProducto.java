package app.model.models.abstracts;

public abstract class AbstractProducto {
    protected int id;
    protected String nombre;
    protected String descripcion;
    protected int precioVenta;
    protected int iva;
    protected int idCategoria;

    public AbstractProducto(int id, String nombre, int precioVenta, int iva, int idCategoria) {
        this.id = id;
        this.nombre = nombre;
        this.precioVenta = precioVenta;
        this.iva = iva;
        this.idCategoria = idCategoria;
    }

    public AbstractProducto(String nombre, int precioVenta, int iva, int idCategoria) {
        this.nombre = nombre;
        this.precioVenta = precioVenta;
        this.iva = iva;
        this.idCategoria = idCategoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(int precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractProducto that = (AbstractProducto) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "AbstractProducto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precioVenta=" + precioVenta +
                ", iva=" + iva +
                ", idCategoria=" + idCategoria +
                '}';
    }
}
