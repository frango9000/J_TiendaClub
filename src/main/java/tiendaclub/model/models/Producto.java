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

public class Producto extends Activable {

    public static final String TABLE_NAME = "productos";
    private static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Arrays.asList("nombre", "descripcion", "precio_venta", "iva", "idCategoria", "activo"));

    protected String nombre;
    protected String descripcion;
    protected int precioVenta;
    protected int iva;
    protected int idCategoria;
    private Categoria categoria;

    {
        this.tableName = TABLE_NAME;
        this.columnNames = COLUMN_NAMES;
    }

    public Producto(int id, String nombre, int idCategoria) {
        super(id);
        setNombre(nombre);
        setIdCategoria(idCategoria);
    }

    public Producto(String nombre, int precioVenta, int iva, int idCategoria) {
        this(0, nombre, idCategoria);
    }

    public Producto(int id, String nombre, Categoria categoria) {
        super(id);
        setNombre(nombre);
        setCategoria(categoria);
    }

    public Producto(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getString(2), rs.getInt(6));
        setDescripcion(rs.getString(3));
        setPrecioVenta(rs.getInt(4));
        setIva(rs.getInt(5));
        setActivo(rs.getBoolean(7));
    }

    @Override
    public void buildStatement(@NonNull PreparedStatement pst) throws SQLException {
        pst.setString(1, getNombre());
        pst.setString(2, getDescripcion());
        pst.setInt(3, getPrecioVenta());
        pst.setInt(4, getIva());
        pst.setInt(5, getIdCategoria());
        pst.setBoolean(6, isActivo());
    }

    @Override
    public <V extends IPersistible> boolean restoreFrom(@NonNull V objectV) {
        if (getId() == objectV.getId() && !this.equals(objectV)) {
            Producto newValues = (Producto) objectV;
            setNombre(newValues.getNombre());
            setDescripcion(newValues.getDescripcion());
            setPrecioVenta(newValues.getPrecioVenta());
            setIva(newValues.getIva());
            setCategoria(newValues.getCategoria());
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
        this.idCategoria = idCategoria;
        updateCategoria();
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
        this.idCategoria = getCategoria().getId();
    }

    private void updateCategoria() {
        setCategoria(DataStore.getCategorias().getIndexId().getCacheValue(getIdCategoria()));
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
        return getId() == producto.getId() && precioVenta == producto.precioVenta && iva == producto.iva
                && idCategoria == producto.idCategoria && isActivo() == producto.isActivo()
                && Objects.equal(nombre, producto.nombre) && Objects.equal(descripcion, producto.descripcion);
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("nombre", nombre).add("descripcion", descripcion).add("precioVenta", precioVenta).add("iva", iva).add("idCategoria", idCategoria).add("categoria", categoria.toString()).add("activo", isActivo()).toString();
    }

    @Override
    public String toStringFormatted() {
        return getId() + " " + getNombre();
    }
}
