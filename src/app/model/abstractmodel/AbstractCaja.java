package app.model.abstractmodel;

public class AbstractCaja {
    private short id;
    private byte idSede;
    private String nombre;

    public AbstractCaja(short id, byte idSede, String nombre) {
        this.id = id;
        this.idSede = idSede;
        this.nombre = nombre;
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public byte getIdSede() {
        return idSede;
    }

    public void setIdSede(byte idSede) {
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
