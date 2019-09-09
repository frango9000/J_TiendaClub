package app.model;

import app.data.DataStore;
import app.data.appdao.CompradoDao;
import app.misc.Flogger;
import casteldao.model.EntityInt;
import casteldao.model.IEntity;
import com.google.common.base.MoreObjects;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import org.checkerframework.checker.nullness.qual.NonNull;

public class Comprado extends EntityInt {

    public static final String TABLE_NAME = "comprados";
    private static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Arrays.asList("idCompra", "idProducto", "cantidad", "precio_unidad"));


    protected int idCompra;
    protected int idProducto;
    protected int cantidad;
    protected int precioUnidad;
    private Compra compra;
    private Producto producto;

    public Comprado() {
        super(0);
    }

    public Comprado(Producto producto, int cantidad, int precioUnidad) {
        super(0);
        setProducto(producto);
        setCantidad(cantidad);
        setPrecioUnidad(precioUnidad);
    }

    @Override
    public boolean setEntity(@NonNull ResultSet rs) {
        try {
            setId(rs.getInt(1));
            setIdCompra(rs.getInt(2));
            setIdProducto(rs.getInt(3));
            setCantidad(rs.getInt(4));
            setPrecioUnidad(rs.getInt(5));
            return true;
        } catch (SQLException e) {
            Flogger.atWarning().withCause(e).log();
            return false;
        }
    }
    @Override
    public boolean buildStatement(@NonNull PreparedStatement pst) {
        try {
            pst.setInt(1, getIdCompra());
            pst.setInt(2, getIdProducto());
            pst.setInt(3, getCantidad());
            pst.setInt(4, getPrecioUnidad());
            return true;
        } catch (SQLException e) {
            Flogger.atWarning().withCause(e).log();
            return false;
        }
    }

    @Override
    public boolean restoreFrom(@NonNull IEntity objectV) {
        if (objectV.getClass().equals(getClass()) && getId() == objectV.getId() && !this.equals(objectV)) {
            Comprado newValues = (Comprado) objectV;
            setCompra(newValues.getCompra());
            setProducto(newValues.getProducto());
            setCantidad(newValues.getCantidad());
            setPrecioUnidad(newValues.getPrecioUnidad());
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
    public CompradoDao getDataStore() {
        return DataStore.getSessionStore().getComprados();
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        setCompra(DataStore.getSessionStore().getCompras().getById().getCacheValue(idCompra));
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        setProducto(DataStore.getSessionStore().getProductos().getById().getCacheValue(idProducto));
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
        this.compra   = compra;
        this.idCompra = getCompra().getId();
    }

    public int getPrecioUnidad() {
        return precioUnidad;
    }

    public void setPrecioUnidad(int precioUnidad) {
        this.precioUnidad = precioUnidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto   = producto;
        this.idProducto = getProducto().getId();
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
        return getId().equals(comprado.getId())
               && getIdCompra() == comprado.getIdCompra()
               && getIdProducto() == comprado.getIdProducto() && getCantidad() == comprado.getCantidad()
               && getPrecioUnidad() == comprado.getPrecioUnidad();
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("id", id)
                          .add("idCompra", idCompra)
                          .add("compra", compra.toString())
                          .add("idProducto", idProducto)
                          .add("producto", producto.toString())
                          .add("cantidad", cantidad)
                          .add("precioUnidad", precioUnidad)
                          .toString();
    }

    @Override
    public String fullToString() {
        return toString();
    }
}
