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
import tiendaclub.model.models.abstracts.Activable;
import tiendaclub.model.models.abstracts.IPersistible;

public class Caja extends Activable {

    public static final String TABLE_NAME = "cajas";
    private static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Arrays.asList("idSede", "nombre", "activo"));
    protected int idSede;
    protected String nombre;
    int id;
    boolean activo;
    private Sede sede;

    {
        this.tableName = TABLE_NAME;
        this.columnNames = COLUMN_NAMES;
    }

    public Caja(int id, int idSede, String nombre) {
        super(id);
        setIdSede(idSede);
        setNombre(nombre);
        updateSede();
    }
    public Caja(int idSede, String nombre) {
        this(0, idSede, nombre);
        updateSede();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean isActivo() {
        return activo;
    }

    @Override
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Caja(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getInt(2), rs.getString(3));
        setActivo(rs.getBoolean(4));
    }

    @Override
    public void buildStatement(@NonNull PreparedStatement pst) throws SQLException {
        pst.setInt(1, getIdSede());
        pst.setString(2, getNombre());
        pst.setBoolean(3, isActivo());
    }

    @Override
    public <V extends IPersistible> boolean restoreFrom(@NonNull V objectV) {
        if (getId() == objectV.getId() && !this.equals(objectV)) {
            Caja newValues = (Caja) objectV;
            setIdSede(newValues.getIdSede());
            setSede(newValues.getSede());
            setNombre(newValues.getNombre());
            setActivo(newValues.isActivo());
            return true;
        }
        return false;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdSede() {
        return idSede;
    }

    public void setIdSede(int idSede) {
        this.idSede = idSede;
        updateSede();
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(@NonNull Sede sede) {
        this.sede = sede;
        this.idSede = getSede().getId();
    }

    private void updateSede() {
        setSede(DataStore.getSedes().getIndexId().getCacheValue(getIdSede()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Caja caja = (Caja) o;
        return getId() == caja.getId() &&
                getIdSede() == caja.getIdSede() &&
                isActivo() == caja.isActivo() &&
                Objects.equal(getNombre(), caja.getNombre());
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("idSede", idSede)
                .add("sede", sede.toString())
                .add("nombre", nombre)
                .add("activo", activo)
                .toString();
    }

    @Override
    public String toStringFormatted() {
        return getId() + " " + getNombre();
    }
}
