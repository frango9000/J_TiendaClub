package tiendaclub.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import org.checkerframework.checker.nullness.qual.NonNull;
import tiendaclub.data.DataStore;
import tiendaclub.data.appdao.VendidoDao;
import tiendaclub.data.casteldao.daomodel.IPersistible;
import tiendaclub.data.casteldao.daomodel.Persistible;

public class Vendido extends Persistible {

    public static final String TABLE_NAME = "vendidos";
    private static final ArrayList<String> COLUMN_NAMES = new ArrayList<>(Arrays.asList("idVenta", "idProducto", "cantidad", "precio_unidad"));


    protected int idProducto;
    protected int idVenta;
    protected int cantidad;
    protected int precioUnidad;
    private Venta venta;
    private Producto producto;

    public Vendido(int id, int idVenta, int idProducto, int cantidad, int precioUnidad) {
        super(id);
        setIdVenta(idVenta);
        setIdProducto(idProducto);
        setCantidad(cantidad);
        setPrecioUnidad(precioUnidad);
    }

    public Vendido(int idVenta, int idProducto, int cantidad, int precioUnidad) {
        this(0, idVenta, idProducto, cantidad, precioUnidad);
    }

    public Vendido(Venta venta, Producto producto, int cantidad, int precioUnidad) {
        super(0);
        setVenta(venta);
        setProducto(producto);
        setCantidad(cantidad);
        setPrecioUnidad(precioUnidad);
    }

    public Vendido(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(4));
    }

    @Override
    public void buildStatement(@NonNull PreparedStatement pst) throws SQLException {
        pst.setInt(1, getIdVenta());
        pst.setInt(2, getIdProducto());
        pst.setInt(3, getCantidad());
        pst.setInt(4, getPrecioUnidad());
    }

    @Override
    public <V extends IPersistible> boolean restoreFrom(@NonNull V objectV) {
        if (getId() == objectV.getId() && !this.equals(objectV)) {
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
        return DataStore.getVendidos();
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
        updateProducto();
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
        updateVenta();
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

    private void updateVenta() {
        setVenta(DataStore.getVentas().getIndexId().getCacheValue(getIdVenta()));
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto   = producto;
        this.idProducto = getProducto().getId();
    }

    private void updateProducto() {
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
        Vendido vendido = (Vendido) o;
        return getId() == vendido.getId() && getIdProducto() == vendido.getIdProducto()
               && getIdVenta() == vendido.getIdVenta() && getCantidad() == vendido.getCantidad()
               && getPrecioUnidad() == vendido.getPrecioUnidad();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
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
}
