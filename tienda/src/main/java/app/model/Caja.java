package app.model;

import app.data.DataStore;
import app.data.appdao.CajaDao;
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


public class Caja extends ActivablePropertyEntity {

    public static final String TABLE_NAME = "cajas";
    private static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Arrays.asList("idSede", "nombre", "active"));

    protected int idSede;
    protected String nombre;
    private Sede sede;

    public Caja() {
        super(0);
    }

    public Caja(Sede sede, String nombre) {
        super(0);
        setSede(sede);
        setNombre(nombre);
    }

    public boolean setEntity(@NonNull ResultSet resultSet) {
        try {
            setId(resultSet.getInt(1));
            setIdSede(resultSet.getInt(2));
            setNombre(resultSet.getString(3));
            setActive(resultSet.getBoolean(4));
            return true;
        } catch (SQLException e) {
            Flogger.atWarning().withCause(e).log();
            return false;
        }
    }

    @Override
    public boolean buildStatement(@NonNull PreparedStatement preparedStatement) {
        try {
            preparedStatement.setInt(1, getIdSede());
            preparedStatement.setString(2, getNombre());
            preparedStatement.setBoolean(3, isActive());
            return true;
        } catch (SQLException e) {
            Flogger.atWarning().withCause(e).log();
            return false;
        }
    }
    @Override
    public boolean restoreFrom(@NonNull IEntity objectV) {
        if (objectV.getClass().equals(getClass()) && getId() == objectV.getId() && !this.equals(objectV)) {
            Caja newValues = (Caja) objectV;
            setSede(newValues.getSede());
            setNombre(newValues.getNombre());
            setActive(newValues.isActive());
            return true;
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public CajaDao getDataStore() {
        return DataStore.getSessionStore().getCajas();
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
        setSede(DataStore.getSessionStore().getSedes().getById().getCacheValue(idSede));
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(@NonNull Sede sede) {
        this.sede   = sede;
        this.idSede = getSede().getId();
    }

    @Override
    public ArrayList<String> getColumnNames() {
        return COLUMN_NAMES;
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
        return getId().equals(caja.getId())
               && getIdSede() == caja.getIdSede()
               && isActive() == caja.isActive()
               && Objects.equal(getNombre(), caja.getNombre());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("id", id)
                          .add("idSede", idSede)
                          .add("sede", sede.toString())
                          .add("nombre", nombre)
                          .add("active", isActive())
                          .toString();
    }

    @Override
    public String toStringFormatted() {
        return getId() + " " + getNombre();
    }
}
