package app.model;

import app.data.DataStore;
import app.data.casteldao.dao.DataSourceIdActive;
import app.data.casteldao.model.IEntity;
import app.misc.Flogger;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import org.checkerframework.checker.nullness.qual.NonNull;

public class Categoria extends ActivablePropertyEntity {

    public static final String TABLE_NAME = "categorias";
    private static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Arrays.asList("nombre", "activo"));

    protected String nombre;

    public Categoria() {
        super(0);
    }

    public Categoria(String nombre) {
        super(0);
        setNombre(nombre);
    }

    @Override
    public boolean setEntity(ResultSet resultSet) {
        try {
            setId(resultSet.getInt(1));
            setNombre(resultSet.getString(2));
            setActive(resultSet.getBoolean(3));
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
            pst.setBoolean(2, isActive());
            return true;
        } catch (SQLException e) {
            Flogger.atWarning().withCause(e).log();
            return false;
        }
    }

    @Override
    public boolean restoreFrom(@NonNull IEntity objectV) {
        if (objectV.getClass().equals(getClass()) && getId() == objectV.getId() && !this.equals(objectV)) {
            Categoria newValues = (Categoria) objectV;
            setNombre(newValues.getNombre());
            setActive(newValues.isActive());
            return true;
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public DataSourceIdActive<Categoria> getDataStore() {
        return DataStore.getCategorias();
    }

    public Set<Producto> getProductos() {
        return DataStore.getProductos().getIndexCategoria().getCacheKeyValues(this);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        Categoria categoria = (Categoria) o;
        return getId().equals(categoria.getId()) &&
               isActive() == categoria.isActive() &&
               Objects.equal(getNombre(), categoria.getNombre());
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("id", id)
                          .add("nombre", nombre)
                          .add("activo", isActive())
                          .toString();
    }
}
