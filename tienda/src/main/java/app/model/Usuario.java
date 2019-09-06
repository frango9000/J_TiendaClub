package app.model;

import app.data.DataStore;
import app.data.appdao.UsuarioDao;
import app.misc.Flogger;
import casteldao.model.IEntity;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import org.checkerframework.checker.nullness.qual.NonNull;

public class Usuario extends ActivablePropertyEntity {

    public static final String TABLE_NAME = "usuarios";
    public static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Arrays.asList("username", "pass", "nombre", "telefono", "email", "direccion", "descripcion", "idAcceso", "active"));

    private String username;
    private String pass;
    private String nombre;
    private String telefono;
    private String email;
    private String direccion;
    private String descripcion;
    private int idAcceso;
    private Acceso acceso;

    public Usuario() {
        super(0);
    }

    public Usuario(String username, Acceso acceso) {
        super(0);
        setUsername(username);
        setAcceso(acceso);
    }

    @Override
    public boolean setEntity(@NonNull ResultSet rs) {
        try {
            setId(rs.getInt(1));
            setUsername(rs.getString(2));
            setPass(rs.getString(3));
            setNombre(rs.getString(4));
            setTelefono(rs.getString(5));
            setEmail(rs.getString(6));
            setDireccion(rs.getString(7));
            setDescripcion(rs.getString(8));
            setIdAcceso(rs.getInt(9));
            setActive(rs.getBoolean(10));
            return true;
        } catch (SQLException e) {
            Flogger.atWarning().withCause(e).log();
            return false;
        }
    }

    public boolean buildStatement(@NonNull PreparedStatement pst) {
        try {
            pst.setString(1, getUsername());
            pst.setString(2, getPass());
            pst.setString(3, getNombre());
            pst.setString(4, getTelefono());
            pst.setString(5, getEmail());
            pst.setString(6, getDireccion());
            pst.setString(7, getDescripcion());
            pst.setInt(8, getIdAcceso());
            pst.setBoolean(9, isActive());
            return true;
        } catch (SQLException e) {
            Flogger.atWarning().withCause(e).log();
            return false;
        }
    }
    @Override
    public boolean restoreFrom(@NonNull IEntity objectV) {
        if (objectV.getClass().equals(getClass()) && getId() == objectV.getId() && !this.equals(objectV)) {
            Usuario newValues = (Usuario) objectV;
            setUsername(newValues.getUsername());
            setPass(newValues.getPass());
            setNombre(newValues.getNombre());
            setTelefono(newValues.getTelefono());
            setEmail(newValues.getEmail());
            setDireccion(newValues.getDireccion());
            setDescripcion(newValues.getDescripcion());
            setAcceso(newValues.getAcceso());
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
    public UsuarioDao getDataStore() {
        return DataStore.getSessionStore().getUsuarios();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
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

    public int getIdAcceso() {
        return idAcceso;
    }

    public void setIdAcceso(int idAcceso) {
        setAcceso(DataStore.getSessionStore().getAccesos().getById().getCacheValue(idAcceso));
    }

    public Acceso getAcceso() {
        return acceso;
    }

    public void setAcceso(@NonNull Acceso acceso) {
        this.acceso   = acceso;
        this.idAcceso = getAcceso().getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Usuario usuario = (Usuario) o;
        return getId().equals(usuario.getId())
               && getIdAcceso() == usuario.getIdAcceso()
               && Objects.equal(getUsername(), usuario.getUsername())
               && Objects.equal(getPass(), usuario.getPass())
               && Objects.equal(getNombre(), usuario.getNombre())
               && Objects.equal(getTelefono(), usuario.getTelefono())
               && Objects.equal(getEmail(), usuario.getEmail())
               && Objects.equal(getDireccion(), usuario.getDireccion())
               && Objects.equal(getDescripcion(), usuario.getDescripcion());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("id", id)
                          .add("username", username)
                          .add("pass", pass)
                          .add("nombre", nombre)
                          .add("telefono", telefono)
                          .add("email", email)
                          .add("direccion", direccion)
                          .add("descripcion", descripcion)
                          .add("idAcceso", idAcceso)
                          .add("acceso", acceso.toString())
                          .toString();
    }

    @Override
    public String toStringFormatted() {
        return MoreObjects.toStringHelper(this)
                          .add("id", id)
                          .add("username", username).toString();
    }
}
