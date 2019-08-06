package tiendaclub.model.models;

import tiendaclub.data.DataStore;
import tiendaclub.model.models.abstracts.AbstractVendido;
import tiendaclub.model.models.abstracts.Persistible;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Vendido extends AbstractVendido {
    public static final String TABLE_NAME = "vendidos";
    private static final ArrayList<String> COL_NAMES = new ArrayList<>(Arrays.asList("idVenta", "idProducto", "cantidad", "precio_unidad"));

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
        pst.setInt(1, idVenta);
        pst.setInt(2, idProducto);
        pst.setInt(3, cantidad);
        pst.setInt(4, precioUnidad);
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
        if (venta != null)
            venta.getVendidos().remove(id);
        this.venta = venta2;
        if (venta != null)
            venta.getVendidos().put(id, this);
    }

    private void updateVenta() {
        setVenta(DataStore.getVentas().get(idVenta));
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
        //  producto.getComprados().remove(id); //No Use
        this.producto = producto2;
        //if(producto!=null)
        //  producto.getComprados().put(id, this); //No Use
    }

    private void updateProducto() {
        setProducto(DataStore.getProductos().get(idProducto));
    }

    @Override
    public int updateOnDb() {
        return DataStore.getVendidos().update(this);
    }

    @Override
    public int refreshFromDb() {
        return DataStore.getVendidos().updateObject(this);
    }

    @Override
    public String insertString() {
        return Persistible.buildInsertString(TABLE_NAME, COL_NAMES);
    }

    @Override
    public String updateString() {
        return Persistible.buildUpdateString(TABLE_NAME, ID_COL_NAME, COL_NAMES, getId());
    }
}
