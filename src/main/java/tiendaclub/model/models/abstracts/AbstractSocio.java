package tiendaclub.model.models.abstracts;

import java.time.LocalDateTime;

public abstract class AbstractSocio extends AbstractIdentifiable {
    protected String dni;
    protected String nombre;
    protected String telefono;
    protected String direccion;
    protected String descripcion;

    protected boolean isActive;
    protected LocalDateTime fechaIn;
    protected LocalDateTime fechaActive;
    protected LocalDateTime fechaInactive;

    public AbstractSocio(int id, String dni, String nombre, LocalDateTime fechaIn) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.fechaIn = fechaIn;
    }

    public AbstractSocio(String dni, String nombre) {
        this.dni = dni;
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDateTime getFechaIn() {
        return fechaIn;
    }

    public void setFechaIn(LocalDateTime fechaIn) {
        this.fechaIn = fechaIn;
    }

    public LocalDateTime getFechaActive() {
        return fechaActive;
    }

    public void setFechaActive(LocalDateTime fechaActive) {
        this.fechaActive = fechaActive;
    }

    public LocalDateTime getFechaInactive() {
        return fechaInactive;
    }

    public void setFechaInactive(LocalDateTime fechaInactive) {
        this.fechaInactive = fechaInactive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractSocio that = (AbstractSocio) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "AbstractSocio{" +
                "id=" + id +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", isActive=" + isActive +
                ", fechaIn=" + fechaIn +
                ", fechaActive=" + fechaActive +
                ", fechaInactive=" + fechaInactive +
                '}';
    }
}
