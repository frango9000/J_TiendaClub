package tiendaclub.model.models.abstracts;

public abstract class AbstractCategoria extends Activable {

    protected String nombre;

    protected AbstractCategoria(int id, String nombre, boolean activo) {
        super(id, activo);
        this.nombre = nombre;
        setActivo(activo);
    }

    protected AbstractCategoria(String nombre, boolean activo) {
        super(0, activo);
        this.nombre = nombre;
        setActivo(activo);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractCategoria that = (AbstractCategoria) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "AbstractCategoria{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
