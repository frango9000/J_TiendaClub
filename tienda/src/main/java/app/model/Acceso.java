package app.model;

import app.data.DataStore;
import app.misc.Flogger;
import casteldao.datasource.DataSourceIdImpl;
import casteldao.model.EntityInt;
import casteldao.model.IEntity;
import com.google.common.base.MoreObjects;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.NonNull;

public class Acceso extends EntityInt {

    public static final String TABLE_NAME = "accesos";
    private static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Collections.singletonList("nivel"));

    private String nivel;

    public Acceso() {
        super(0);
    }

    public Acceso(int id, String nivel) {
        super(id);
        this.nivel = nivel;
    }

    public Acceso(String nivel) {
        this(0, nivel);
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(@NonNull String nivel) {
        this.nivel = nivel;
    }

    @Override
    @SuppressWarnings("unchecked")
    public DataSourceIdImpl<Acceso> getDataStore() {
        return DataStore.getSessionStore().getAccesos();
    }

    @Override
    public boolean setEntity(@NonNull ResultSet rs) {
        try {
            setId(rs.getInt(1));
            setNivel(rs.getString(2));
            return true;
        } catch (SQLException e) {
            Flogger.atWarning().withCause(e).log();
            return false;
        }
    }

    @Override
    public boolean buildStatement(@NonNull PreparedStatement preparedStatement) {
        try {
            preparedStatement.setString(1, getNivel());
            return true;
        } catch (SQLException e) {
            Flogger.atWarning().withCause(e).log();
            return false;
        }
    }
    @Override
    public boolean restoreFrom(@NonNull IEntity objectV) {
        if (objectV.getClass().equals(getClass()) && getId() == objectV.getId() && !this.equals(objectV)) {
            Acceso newValues = (Acceso) objectV;
            setNivel(newValues.getNivel());
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<String> getColumnNames() {
        return COLUMN_NAMES;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        Acceso acceso = (Acceso) o;
        return getId().equals(acceso.getId()) &&
               Objects.equals(getNivel(), acceso.getNivel());
    }

    @Override
    public String fullToString() {
        return MoreObjects.toStringHelper(this)
                          .add("id", id)
                          .add("nivel", nivel)
                          .toString();
    }

    @Override
    public String toString() {
        return getId() + " " + getNivel();
    }
}
