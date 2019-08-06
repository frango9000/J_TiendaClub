package tiendaclub.model.models.abstracts;

public abstract class AbstractCategoria extends Persistible {

    protected String nombre;

    public AbstractCategoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public AbstractCategoria(String nombre) {
        this.nombre = nombre;
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
