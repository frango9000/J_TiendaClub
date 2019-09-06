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
import java.util.Set;
import org.checkerframework.checker.nullness.qual.NonNull;

public class Sede extends ActivablePropertyEntity {

    public static final String TABLE_NAME = "sedes";
    private static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Arrays.asList("nombre", "telefono", "direccion", "active"));

    protected String nombre;
    protected String telefono;
    protected String direccion;

    public Sede() {
        super(0);
    }

    public Sede(String nombre) {
        super(0);
        setNombre(nombre);
    }

    @Override
    public boolean setEntity(@NonNull ResultSet rs) {
        try {
            setId(rs.getInt(1));
            setNombre(rs.getString(2));
            setDireccion(rs.getString(3));
            setTelefono(rs.getString(4));
            setActive(rs.getBoolean(5));
            return true;
        } catch (SQLException e) {
            Flogger.atWarning().withCause(e).log();
            return false;
        }
    }
    @Override
    public boolean buildStatement(@NonNull PreparedStatement pst) {
        try {
            pst.setString(1, getNombre());
            pst.setString(2, getTelefono());
            pst.setString(3, getDireccion());
            pst.setBoolean(4, isActive());
            return true;
        } catch (SQLException e) {
            Flogger.atWarning().withCause(e).log();
            return false;
        }
    }
    @Override
    public boolean restoreFrom(@NonNull IEntity objectV) {
        if (objectV.getClass().equals(getClass()) && getId() == objectV.getId() && !this.equals(objectV)) {
            Sede newValues = (Sede) objectV;
            setNombre(newValues.getNombre());
            setTelefono(newValues.getTelefono());
            setDireccion(newValues.getDireccion());
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
    public DataSourceIdActive<Sede> getDataStore() {
        return DataStore.getSessionStore().getSedes();
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

    public Set<Caja> getCajas() {
        return DataStore.getSessionStore().getCajas().getIndexSede().getCacheKeyValues(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Sede sede = (Sede) o;
        return getId().equals(sede.getId())
               && isActive() == sede.isActive()
               && Objects.equal(getNombre(), sede.getNombre())
               && Objects.equal(getTelefono(), sede.getTelefono())
               && Objects.equal(getDireccion(), sede.getDireccion());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("id", id)
                          .add("nombre", nombre)
                          .add("telefono", telefono)
                          .add("direccion", direccion)
                          .add("active", isActive())
                          .toString();
    }

    @Override
    public String toStringFormatted() {
        return getId() + " " + getNombre();
    }
}
