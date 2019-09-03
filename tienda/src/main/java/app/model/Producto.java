package app.model;

import app.data.DataStore;
import app.data.appdao.ProductoDao;
import app.data.casteldao.model.IEntity;
import app.misc.Flogger;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import org.checkerframework.checker.nullness.qual.NonNull;

public class Producto extends ActivablePropertyEntity {

    public static final String TABLE_NAME = "productos";
    private static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Arrays.asList("nombre", "descripcion", "precio_venta", "iva", "idCategoria", "activo"));

    protected String nombre;
    protected String descripcion;
    protected int precioVenta;
    protected int iva;
    protected int idCategoria;
    private Categoria categoria;

    public Producto() {
        super(0);
    }

    public Producto(String nombre, Categoria categoria) {
        super(0);
        setNombre(nombre);
        setIdCategoria(idCategoria);
    }

    @Override
    public boolean setEntity(@NonNull ResultSet rs) {
        try {
            setId(rs.getInt(1));
            setNombre(rs.getString(2));
            setDescripcion(rs.getString(3));
            setPrecioVenta(rs.getInt(4));
            setIva(rs.getInt(5));
            setIdCategoria(rs.getInt(6));
            setActive(rs.getBoolean(7));
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
            pst.setString(2, getDescripcion());
            pst.setInt(3, getPrecioVenta());
            pst.setInt(4, getIva());
            pst.setInt(5, getIdCategoria());
            pst.setBoolean(6, isActive());
            return true;
        } catch (SQLException e) {
            Flogger.atWarning().withCause(e).log();
            return false;
        }
    }

    @Override
    public boolean restoreFrom(@NonNull IEntity objectV) {
        if (objectV.getClass().equals(getClass()) && getId() == objectV.getId() && !this.equals(objectV)) {
            Producto newValues = (Producto) objectV;
            setNombre(newValues.getNombre());
            setDescripcion(newValues.getDescripcion());
            setPrecioVenta(newValues.getPrecioVenta());
            setIva(newValues.getIva());
            setCategoria(newValues.getCategoria());
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
    public ProductoDao getDataStore() {
        return DataStore.getProductos();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(int precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        setCategoria(DataStore.getCategorias().getById().getCacheValue(idCategoria));
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria   = categoria;
        this.idCategoria = getCategoria().getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Producto producto = (Producto) o;
        return getId().equals(producto.getId())
               && precioVenta == producto.precioVenta
               && iva == producto.iva
               && idCategoria == producto.idCategoria
               && isActive() == producto.isActive()
               && Objects.equal(nombre, producto.nombre)
               && Objects.equal(descripcion, producto.descripcion);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("id", id)
                          .add("nombre", nombre)
                          .add("descripcion", descripcion)
                          .add("precioVenta", precioVenta)
                          .add("iva", iva)
                          .add("idCategoria", idCategoria)
                          .add("categoria", categoria.toString())
                          .add("activo", isActive())
                          .toString();
    }

    @Override
    public String toStringFormatted() {
        return getId() + " " + getNombre();
    }
}
