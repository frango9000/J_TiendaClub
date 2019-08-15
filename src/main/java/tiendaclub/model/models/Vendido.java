package tiendaclub.model.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import tiendaclub.data.DataStore;
import tiendaclub.model.models.abstracts.AbstractVendido;

public class Vendido extends AbstractVendido {

    public static final String TABLE_NAME = "vendidos";
    private static final ArrayList<String> COL_NAMES = new ArrayList<>(
            Arrays.asList("idVenta", "idProducto", "cantidad", "precio_unidad"));

    private Venta venta;
    private Producto producto;

    public Vendido(int id, int idVenta, int idProducto, int cantidad, int precioUnidad) {
        super(id, idVenta, idProducto, cantidad, precioUnidad);
        updateVenta();
        updateProducto();
    }

    public Vendido(int idVenta, int idProducto, int cantidad, int precioUnidad) {
        super(idVenta, idProducto, cantidad, precioUnidad);
        updateVenta();
        updateProducto();
    }

    public Vendido(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(4));
    }

    @Override
    public void buildStatement(PreparedStatement pst) throws SQLException {
        pst.setInt(1, getIdVenta());
        pst.setInt(2, getIdProducto());
        pst.setInt(3, getCantidad());
        pst.setInt(4, getPrecioUnidad());
    }

    @Override
    public void updateObject(ResultSet rs) throws SQLException {
        //setId(rs.getInt(1));
        setIdVenta(rs.getInt(2));
        setIdProducto(rs.getInt(3));
        setCantidad(rs.getInt(4));
        setPrecioUnidad(rs.getInt(5));
    }

    @Override
    public void setIdVenta(int idVenta) {
        super.setIdVenta(idVenta);
        updateVenta();
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta2) {
        if (venta != null) {
            venta.getVendidos().remove(getId());
        }
        this.venta = venta2;
        if (venta != null) {
            venta.getVendidos().put(getId(), this);
        }
    }

    private void updateVenta() {
        setVenta(DataStore.getVentas().getIdIndex().getCacheValue(getIdVenta()));
    }

    @Override
    public void setIdProducto(int idProducto) {
        super.setIdProducto(idProducto);
        updateProducto();
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto2) {
        //if(producto!=null)
        //  producto.getComprados().remove(getId()); //No Use
        this.producto = producto2;
        //if(producto!=null)
        //  producto.getComprados().put(getId(), this); //No Use
    }

    private void updateProducto() {
        setProducto(DataStore.getProductos().getIdIndex().getCacheValue(getIdProducto()));
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public ArrayList<String> getColNames() {
        return COL_NAMES;
    }
}
