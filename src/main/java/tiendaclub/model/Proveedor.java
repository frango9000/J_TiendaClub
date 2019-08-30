package tiendaclub.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import org.checkerframework.checker.nullness.qual.NonNull;
import tiendaclub.data.DataStore;
import tiendaclub.data.casteldao.daomodel.Activable;
import tiendaclub.data.casteldao.daomodel.IPersistible;
import tiendaclub.data.casteldao.daomodel.IndexIdActiveDao;

public class Proveedor extends Activable {

    public static final String TABLE_NAME = "proveedores";
    private static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Arrays.asList("nif", "nombre", "telefono", "email", "direccion", "descripcion", "activo"));

    protected String nif;
    protected String nombre;
    protected String telefono;
    protected String email;
    protected String direccion;
    protected String descripcion;

    public Proveedor(int id, String nif) {
        super(id);
        setNif(nif);
    }

    public Proveedor(String nif) {
        this(0, nif);
    }

    public Proveedor(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getString(2));
        setNombre(rs.getString(3));
        setTelefono(rs.getString(4));
        setEmail(rs.getString(5));
        setDireccion(rs.getString(6));
        setDescripcion(rs.getString(7));
        setActivo(rs.getBoolean(8));
    }

    @Override
    public void buildStatement(@NonNull PreparedStatement pst) throws SQLException {
        pst.setString(1, getNif());
        pst.setString(2, getNombre());
        pst.setString(3, getTelefono());
        pst.setString(4, getEmail());
        pst.setString(5, getDireccion());
        pst.setString(6, getDescripcion());
        pst.setBoolean(7, isActivo());
    }


    @Override
    public <V extends IPersistible> boolean restoreFrom(@NonNull V objectV) {
        if (getId() == objectV.getId() && !this.equals(objectV)) {
            Proveedor newValues = (Proveedor) objectV;
            setNif(newValues.getNif());
            setNombre(newValues.getNombre());
            setTelefono(newValues.getTelefono());
            setEmail(newValues.getEmail());
            setDireccion(newValues.getDireccion());
            setDescripcion(newValues.getDescripcion());
            setActivo(newValues.isActivo());
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
    public IndexIdActiveDao<Proveedor> getDataStore() {
        return DataStore.getProveedores();
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
        return getId() == proveedor.getId() && isActivo() == proveedor.isActivo()
               && Objects.equal(getNif(), proveedor.getNif()) && Objects.equal(getNombre(), proveedor.getNombre())
               && Objects.equal(getTelefono(), proveedor.getTelefono())
               && Objects.equal(getEmail(), proveedor.getEmail())
               && Objects.equal(getDireccion(), proveedor.getDireccion())
               && Objects.equal(getDescripcion(), proveedor.getDescripcion());
    }

    @Override
    public int hashCode() {
        return getId();
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
                          .add("activo", isActivo())
                          .toString();
    }

    @Override
    public String toStringFormatted() {
        return getId() + " " + getNombre();
    }
}
