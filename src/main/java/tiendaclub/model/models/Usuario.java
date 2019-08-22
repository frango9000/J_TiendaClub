package tiendaclub.model.models;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import org.checkerframework.checker.nullness.qual.NonNull;
import tiendaclub.data.DataStore;
import tiendaclub.model.models.core.Activable;
import tiendaclub.model.models.core.IPersistible;

public class Usuario extends Activable {

    public static final String TABLE_NAME = "usuarios";
    public static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Arrays.asList("username", "pass", "nombre", "telefono", "email", "direccion", "descripcion", "idAcceso", "activo"));

    private String username;
    private String pass;
    private String nombre;
    private String telefono;
    private String email;
    private String direccion;
    private String descripcion;
    private int idAcceso;
    private Acceso acceso;

    {
        this.tableName = TABLE_NAME;
        this.columnNames = COLUMN_NAMES;
    }

    public Usuario(int id, String username, int idAcceso) {
        super(id);
        setUsername(username);
        setIdAcceso(idAcceso);
    }

    public Usuario(String username, int idAcceso) {
        this(0, username, idAcceso);
    }

    public Usuario(String username, Acceso acceso) {
        super(0);
        setUsername(username);
        setAcceso(acceso);
    }

    public Usuario(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getString(2), rs.getInt(9));
        setPass(rs.getString(3));
        setNombre(rs.getString(4));
        setTelefono(rs.getString(5));
        setEmail(rs.getString(6));
        setDireccion(rs.getString(7));
        setDescripcion(rs.getString(8));
        setActivo(rs.getBoolean(10));
    }


    public void buildStatement(@NonNull PreparedStatement pst) throws SQLException {
        pst.setString(1, getUsername());
        pst.setString(2, getPass());
        pst.setString(3, getNombre());
        pst.setString(4, getTelefono());
        pst.setString(5, getEmail());
        pst.setString(6, getDireccion());
        pst.setString(7, getDescripcion());
        pst.setInt(8, getIdAcceso());
        pst.setBoolean(9, isActivo());
    }

    @Override
    public <V extends IPersistible> boolean restoreFrom(@NonNull V objectV) {
        if (getId() == objectV.getId() && !this.equals(objectV)) {
            Usuario newValues = (Usuario) objectV;
            setUsername(newValues.getUsername());
            setPass(newValues.getPass());
            setNombre(newValues.getNombre());
            setTelefono(newValues.getTelefono());
            setEmail(newValues.getEmail());
            setDireccion(newValues.getDireccion());
            setDescripcion(newValues.getDescripcion());
            setAcceso(newValues.getAcceso());
            setActivo(newValues.isActivo());
            return true;
        }
        return false;
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
        this.idAcceso = idAcceso;
        updateAcceso();
    }

    public Acceso getAcceso() {
        return acceso;
    }

    public void setAcceso(@NonNull Acceso acceso) {
        this.acceso = acceso;
        this.idAcceso = getAcceso().getId();
    }

    private void updateAcceso() {
        setAcceso(DataStore.getAccesos().getIndexId().getCacheValue(getIdAcceso()));
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
        return getId() == usuario.getId() && getIdAcceso() == usuario.getIdAcceso()
                && Objects.equal(getUsername(), usuario.getUsername()) && Objects.equal(getPass(), usuario.getPass())
                && Objects.equal(getNombre(), usuario.getNombre())
                && Objects.equal(getTelefono(), usuario.getTelefono()) && Objects.equal(getEmail(), usuario.getEmail())
                && Objects.equal(getDireccion(), usuario.getDireccion())
                && Objects.equal(getDescripcion(), usuario.getDescripcion());
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("username", username).add("pass", pass).add("nombre", nombre).add("telefono", telefono).add("email", email).add("direccion", direccion).add("descripcion", descripcion).add("idAcceso", idAcceso).add("acceso", acceso.toString()).toString();
    }

    @Override
    public String toStringFormatted() {
        return MoreObjects.toStringHelper(this).add("id", id).add("username", username).toString();
    }
}
