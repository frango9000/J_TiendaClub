package tiendaclub.model.models.abstracts;

public abstract class AbstractCaja extends Activable {
    protected int idSede;
    protected String nombre;

    public AbstractCaja(int id, int idSede, String nombre) {
        super(id);
        this.idSede = idSede;
        this.nombre = nombre;
    }

    protected AbstractCaja(int idSede, String nombre) {
        super(0);
        this.idSede = idSede;
        this.nombre = nombre;
    }

    public int getIdSede() {
        return idSede;
    }

    public void setIdSede(int idSede) {
        this.idSede = idSede;
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

        AbstractCaja that = (AbstractCaja) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "AbstractCaja{" +
                "id=" + id +
                ", idSede=" + idSede +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
