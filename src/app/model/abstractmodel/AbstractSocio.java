package app.model.abstractmodel;

import java.time.LocalDateTime;

public class AbstractSocio {
    private short id;
    private String dni;
    private String nombre;
    private String telefono;
    private String direccion;
    private String descripcion;

    private boolean isActive;
    private LocalDateTime fechaIn;
    private LocalDateTime fechaActive;
    private LocalDateTime fechaInactive;

    public AbstractSocio(short id, String dni, String nombre, String telefono, String direccion, String descripcion, LocalDateTime fechaIn) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.isActive = true;
        this.fechaIn = fechaIn;
        this.fechaActive = fechaIn;
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
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
