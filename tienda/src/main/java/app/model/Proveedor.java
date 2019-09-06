package app.model;

import app.data.DataStore;
import app.misc.Flogger;
import casteldao.datasource.DataSourceIdActive;
import casteldao.model.IEntity;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import org.checkerframework.checker.nullness.qual.NonNull;

public class Proveedor extends ActivablePropertyEntity {

    public static final String TABLE_NAME = "proveedores";
    private static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Arrays.asList("nif", "nombre", "telefono", "email", "direccion", "descripcion", "activo"));

    protected String nif;
    protected String nombre;
    protected String telefono;
    protected String email;
    protected String direccion;
    protected String descripcion;

    public Proveedor() {
        super(0);
    }

    public Proveedor(String nif) {
        super(0);
        setNif(nif);
    }

    @Override
    public boolean setEntity(@NonNull ResultSet rs) {
        try {
            setId(rs.getInt(1));
            setNif(rs.getString(2));
            setNombre(rs.getString(3));
            setTelefono(rs.getString(4));
            setEmail(rs.getString(5));
            setDireccion(rs.getString(6));
            setDescripcion(rs.getString(7));
            setActive(rs.getBoolean(8));
            return true;
        } catch (SQLException e) {
            Flogger.atWarning().withCause(e).log();
            return false;
        }
    }

    @Override
    public boolean buildStatement(@NonNull PreparedStatement pst) {
        try {
            pst.setString(1, getNif());
            pst.setString(2, getNombre());
            pst.setString(3, getTelefono());
            pst.setString(4, getEmail());
            pst.setString(5, getDireccion());
            pst.setString(6, getDescripcion());
            pst.setBoolean(7, isActive());
            return true;
        } catch (SQLException e) {
            Flogger.atWarning().withCause(e).log();
            return false;
        }
    }

    @Override
    public boolean restoreFrom(@NonNull IEntity objectV) {
        if (objectV.getClass().equals(getClass()) && getId() == objectV.getId() && !this.equals(objectV)) {
            Proveedor newValues = (Proveedor) objectV;
            setNif(newValues.getNif());
            setNombre(newValues.getNombre());
            setTelefono(newValues.getTelefono());
            setEmail(newValues.getEmail());
            setDireccion(newValues.getDireccion());
            setDescripcion(newValues.getDescripcion());
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
    public DataSourceIdActive<Proveedor> getDataStore() {
        return DataStore.getSessionStore().getProveedores();
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Proveedor proveedor = (Proveedor) o;
        return getId().equals(proveedor.getId())
               && isActive() == proveedor.isActive()
               && Objects.equal(getNif(), proveedor.getNif())
               && Objects.equal(getNombre(), proveedor.getNombre())
               && Objects.equal(getTelefono(), proveedor.getTelefono())
               && Objects.equal(getEmail(), proveedor.getEmail())
               && Objects.equal(getDireccion(), proveedor.getDireccion())
               && Objects.equal(getDescripcion(), proveedor.getDescripcion());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("id", id)
                          .add("nif", nif)
                          .add("nombre", nombre)
                          .add("telefono", telefono)
                          .add("email", email)
                          .add("direccion", direccion)
                          .add("descripcion", descripcion)
                          .add("activo", isActive())
                          .toString();
    }

    @Override
    public String toStringFormatted() {
        return getId() + " " + getNombre();
    }
}
