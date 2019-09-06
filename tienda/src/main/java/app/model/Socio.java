package app.model;

import app.data.DataStore;
import app.data.appdao.SocioDao;
import app.misc.DateUtils;
import app.misc.Flogger;
import casteldao.model.IEntity;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import org.checkerframework.checker.nullness.qual.NonNull;

public class Socio extends ActivablePropertyEntity {

    public static final String TABLE_NAME = "socios";
    private static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Arrays.asList("dni", "nombre", "telefono", "email", "direccion", "descripcion", "fecha_in", "activo"));

    protected String dni;
    protected String nombre;
    protected String telefono;
    protected String email;
    protected String direccion;
    protected String descripcion;
    protected LocalDateTime fechaIn;

    public Socio() {
        super(0);
    }

    public Socio(int id, String dni) {
        super(id);
        setDni(dni);

    }

    public Socio(String dni) {
        this(0, dni);
    }

    @Override
    public boolean setEntity(@NonNull ResultSet rs) {
        try {
            setId(rs.getInt(1));
            setDni(rs.getString(2));
            setNombre(rs.getString(3));
            setTelefono(rs.getString(4));
            setEmail(rs.getString(5));
            setDireccion(rs.getString(6));
            setDescripcion(rs.getString(7));
            setFechaIn(DateUtils.toLocalDateTime(rs.getTimestamp(8)));
            setActive(rs.getBoolean(9));
            return true;
        } catch (SQLException e) {
            Flogger.atWarning().withCause(e).log();
            return false;
        }
    }
    @Override
    public boolean buildStatement(@NonNull PreparedStatement pst) {
        try {
            pst.setString(1, getDni());
            pst.setString(2, getNombre());
            pst.setString(3, getTelefono());
            pst.setString(4, getEmail());
            pst.setString(5, getDireccion());
            pst.setString(6, getDescripcion());
            pst.setTimestamp(7, DateUtils.toTimestamp(getFechaIn()));
            pst.setBoolean(8, isActive());
            return true;
        } catch (SQLException e) {
            Flogger.atWarning().withCause(e).log();
            return false;
        }
    }
    @Override
    public boolean restoreFrom(@NonNull IEntity objectV) {
        if (objectV.getClass().equals(getClass()) && getId() == objectV.getId() && !this.equals(objectV)) {
            Socio newValues = (Socio) objectV;
            setDni(newValues.getDni());
            setNombre(newValues.getNombre());
            setTelefono(newValues.getTelefono());
            setEmail(newValues.getEmail());
            setDireccion(newValues.getDireccion());
            setDescripcion(newValues.getDescripcion());
            setFechaIn(newValues.getFechaIn());
            setActive(newValues.isActive());
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<String> getColumnNames() {
        return COLUMN_NAMES;
    }

    @Override
    @SuppressWarnings("unchecked")
    public SocioDao getDataStore() {
        return DataStore.getSessionStore().getSocios();
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
        return getId().equals(socio.getId())
               && isActive() == socio.isActive()
               && Objects.equal(getDni(), socio.getDni())
               && Objects.equal(getNombre(), socio.getNombre())
               && Objects.equal(getTelefono(), socio.getTelefono())
               && Objects.equal(getEmail(), socio.getEmail())
               && Objects.equal(getDireccion(), socio.getDireccion())
               && Objects.equal(getDescripcion(), socio.getDescripcion())
               && Objects.equal(getFechaIn(), socio.getFechaIn());
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
                          .add("activo", isActive())
                          .toString();
    }

    @Override
    public String toStringFormatted() {
        return getId() + " " + getDni();
    }
}
