package tiendaclub.model.models;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import org.checkerframework.checker.nullness.qual.NonNull;
import tiendaclub.data.framework.model.IPersistible;
import tiendaclub.misc.DateUtils;
import tiendaclub.model.models.core.Activable;

public class Socio extends Activable {

    public static final String TABLE_NAME = "socios";
    private static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Arrays.asList("dni", "nombre", "telefono", "email", "direccion", "descripcion", "fecha_in", "activo"));

    protected String dni;
    protected String nombre;
    protected String telefono;
    protected String email;
    protected String direccion;
    protected String descripcion;
    protected LocalDateTime fechaIn;

    {
        this.tableName   = TABLE_NAME;
        this.columnNames = COLUMN_NAMES;
    }

    public Socio(int id, String dni) {
        super(id);
        setDni(dni);

    }

    public Socio(String dni) {
        this(0, dni);
    }

    public Socio(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getString(2));
        setNombre(rs.getString(3));
        setTelefono(rs.getString(4));
        setEmail(rs.getString(5));
        setDireccion(rs.getString(6));
        setDescripcion(rs.getString(7));
        setFechaIn(DateUtils.toLocalDateTime(rs.getTimestamp(8)));
        setActivo(rs.getBoolean(9));
    }

    @Override
    public void buildStatement(@NonNull PreparedStatement pst) throws SQLException {
        pst.setString(1, getDni());
        pst.setString(2, getNombre());
        pst.setString(3, getTelefono());
        pst.setString(4, getEmail());
        pst.setString(5, getDireccion());
        pst.setString(6, getDescripcion());
        pst.setTimestamp(7, DateUtils.toTimestamp(getFechaIn()));
        pst.setBoolean(8, isActivo());
    }

    @Override
    public <V extends IPersistible> boolean restoreFrom(@NonNull V objectV) {
        if (getId() == objectV.getId() && !this.equals(objectV)) {
            Socio newValues = (Socio) objectV;
            setDni(newValues.getDni());
            setNombre(newValues.getNombre());
            setTelefono(newValues.getTelefono());
            setEmail(newValues.getEmail());
            setDireccion(newValues.getDireccion());
            setDescripcion(newValues.getDescripcion());
            setFechaIn(newValues.getFechaIn());
            setActivo(newValues.isActivo());
            return true;
        }
        return false;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public LocalDateTime getFechaIn() {
        return fechaIn;
    }

    public void setFechaIn(LocalDateTime fechaIn) {
        this.fechaIn = fechaIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Socio socio = (Socio) o;
        return getId() == socio.getId() && isActivo() == socio.isActivo() && Objects.equal(getDni(), socio.getDni())
               && Objects.equal(getNombre(), socio.getNombre())
               && Objects.equal(getTelefono(), socio.getTelefono())
               && Objects.equal(getEmail(), socio.getEmail())
               && Objects.equal(getDireccion(), socio.getDireccion())
               && Objects.equal(getDescripcion(), socio.getDescripcion())
               && Objects.equal(getFechaIn(), socio.getFechaIn());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("id", id)
                          .add("dni", dni)
                          .add("nombre", nombre)
                          .add("telefono", telefono)
                          .add("email", email)
                          .add("direccion", direccion)
                          .add("descripcion", descripcion)
                          .add("fechaIn", fechaIn)
                          .add("activo", isActivo())
                          .toString();
    }

    @Override
    public String toStringFormatted() {
        return getId() + " " + getDni();
    }
}
