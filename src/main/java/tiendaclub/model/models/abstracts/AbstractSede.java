package tiendaclub.model.models.abstracts;

public abstract class AbstractSede extends Activable {
    protected String nombre;
    protected String telefono;
    protected String direccion;

    public AbstractSede(int id, String nombre, String telefono, String direccion) {
        super(id);
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public AbstractSede(String nombre, String telefono, String direccion) {
        super(0);
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractSede that = (AbstractSede) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "AbstractSede{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
