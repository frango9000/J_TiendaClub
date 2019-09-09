package app.model;

import app.data.DataStore;
import app.data.appdao.VendidoDao;
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

public class Vendido extends EntityInt {

    public static final String TABLE_NAME = "vendidos";
    private static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Arrays.asList("idVenta", "idProducto", "cantidad", "precio_unidad"));


    protected int idProducto;
    protected int idVenta;
    protected int cantidad;
    protected int precioUnidad;
    private Venta venta;
    private Producto producto;


    public Vendido() {
        super(0);
    }

    public Vendido(Producto producto, int cantidad, int precioUnidad) {
        super(0);
        setProducto(producto);
        setCantidad(cantidad);
        setPrecioUnidad(precioUnidad);
    }

    @Override
    public boolean setEntity(@NonNull ResultSet rs) {
        try {
            setId(rs.getInt(1));
            setIdVenta(rs.getInt(2));
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
            pst.setInt(1, getIdVenta());
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
            Vendido newValues = (Vendido) objectV;
            setVenta(newValues.getVenta());
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
    public VendidoDao getDataStore() {
        return DataStore.getSessionStore().getVendidos();
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        setProducto(DataStore.getSessionStore().getProductos().getById().getCacheValue(idProducto));
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        setVenta(DataStore.getSessionStore().getVentas().getById().getCacheValue(idVenta));
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta   = venta;
        this.idVenta = getVenta().getId();
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
        Vendido vendido = (Vendido) o;
        return getId().equals(vendido.getId())
               && getIdProducto() == vendido.getIdProducto()
               && getIdVenta() == vendido.getIdVenta()
               && getCantidad() == vendido.getCantidad()
               && getPrecioUnidad() == vendido.getPrecioUnidad();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("id", id)
                          .add("idProducto", idProducto)
                          .add("producto", producto.toString())
                          .add("idVenta", idVenta)
                          .add("venta", venta.toString())
                          .add("cantidad", cantidad)
                          .add("precioUnidad", precioUnidad)
                          .toString();
    }

    @Override
    public String fullToString() {
        return toString();
    }
}
