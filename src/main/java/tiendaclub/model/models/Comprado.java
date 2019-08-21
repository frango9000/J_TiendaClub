package tiendaclub.model.models;

import com.google.common.base.MoreObjects;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import org.checkerframework.checker.nullness.qual.NonNull;
import tiendaclub.data.DataStore;
import tiendaclub.model.models.core.IPersistible;
import tiendaclub.model.models.core.Persistible;

public class Comprado extends Persistible {

    public static final String TABLE_NAME = "comprados";
    private static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Arrays.asList("idCompra", "idProducto", "cantidad", "precio_unidad"));


    protected int idCompra;
    protected int idProducto;
    protected int cantidad;
    protected int precioUnidad;
    private Compra compra;
    private Producto producto;

    {
        this.tableName = TABLE_NAME;
        this.columnNames = COLUMN_NAMES;
    }

    private Comprado(int id, int idCompra, int idProducto, int cantidad, int precioUnidad) {
        super(id);
        setIdCompra(idCompra);
        setIdProducto(idProducto);
        setCantidad(cantidad);
        setPrecioUnidad(precioUnidad);
    }

    public Comprado(int idCompra, int idProducto, int cantidad, int precioUnidad) {
        this(0, idCompra, idProducto, cantidad, precioUnidad);
    }

    public Comprado(Compra compra, Producto producto, int cantidad, int precioUnidad) {
        super(0);
        setCompra(compra);
        setProducto(producto);
        setCantidad(cantidad);
        setPrecioUnidad(precioUnidad);
    }

    public Comprado(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
    }

    @Override
    public void buildStatement(@NonNull PreparedStatement pst) throws SQLException {
        pst.setInt(1, getIdCompra());
        pst.setInt(2, getIdProducto());
        pst.setInt(3, getCantidad());
        pst.setInt(4, getPrecioUnidad());
    }

    @Override
    public <V extends IPersistible> boolean restoreFrom(@NonNull V objectV) {
        if (getId() == objectV.getId() && !this.equals(objectV)) {
            Comprado newValues = (Comprado) objectV;
            setCompra(newValues.getCompra());
            setProducto(newValues.getProducto());
            setCantidad(newValues.getCantidad());
            setPrecioUnidad(newValues.getPrecioUnidad());
            return true;
        }
        return false;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
        updateCompra();
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
        updateProducto();
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }


    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
        this.idCompra = getCompra().getId();
    }

    public int getPrecioUnidad() {
        return precioUnidad;
    }

    public void setPrecioUnidad(int precioUnidad) {
        this.precioUnidad = precioUnidad;
    }

    public void updateCompra() {
        setCompra(DataStore.getCompras().getIndexId().getCacheValue(getIdCompra()));
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        this.idProducto = getProducto().getId();
    }

    public void updateProducto() {
        setProducto(DataStore.getProductos().getIndexId().getCacheValue(getIdProducto()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comprado comprado = (Comprado) o;
        return getId() == comprado.getId() && getIdCompra() == comprado.getIdCompra()
                && getIdProducto() == comprado.getIdProducto() && getCantidad() == comprado.getCantidad()
                && getPrecioUnidad() == comprado.getPrecioUnidad();
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("idCompra", idCompra).add("compra", compra.toString()).add("idProducto", idProducto).add("producto", producto.toString()).add("cantidad", cantidad).add("precioUnidad", precioUnidad).toString();
    }
}
