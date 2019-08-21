package tiendaclub.model.models;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import org.checkerframework.checker.nullness.qual.NonNull;
import tiendaclub.model.models.core.Activable;
import tiendaclub.model.models.core.IPersistible;

public class Categoria extends Activable {

    public static final String TABLE_NAME = "categorias";
    private static final ArrayList<String> COL_NAMES = new ArrayList<>(Arrays.asList("nombre", "activo"));

    protected String nombre;

    public Categoria(int id, String nombre) {
        super(id);
        setNombre(nombre);
    }

    public Categoria(String nombre) {
        this(0, nombre);
    }

    public Categoria(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getString(2));
        setActivo(rs.getBoolean(3));
    }

    @Override
    public void buildStatement(@NonNull PreparedStatement pst) throws SQLException {
        pst.setString(1, getNombre());
        pst.setBoolean(2, isActivo());
    }

    @Override
    public <V extends IPersistible> boolean restoreFrom(@NonNull V objectV) {
        return false;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        return getId() == categoria.getId() && isActivo() == categoria.isActivo()
                && Objects.equal(getNombre(), categoria.getNombre());
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("nombre", nombre).add("activo", isActivo()).toString();
    }
}
