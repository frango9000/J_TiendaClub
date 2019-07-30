package app.model.models;

import app.model.IPersistible;
import app.model.models.abstracts.AbstractVendido;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Vendido extends AbstractVendido implements IPersistible {
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
    public void buildStatement(PreparedStatement preparedStatement) throws SQLException {

    }

    @Override
    public void updateObject(ResultSet rs) throws SQLException {

    }

    @Override
    public void setIdVenta(int idVenta) {
        super.setIdVenta(idVenta);
        updateVenta();
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    private void updateVenta() {
        if (venta != null)
            venta.getVendidos().remove(id);
        //TODO DAO
        //venta = DAO venta . get( idVenta );
        venta.getVendidos().put(id, this);

    }

    @Override
    public void setIdProducto(int idProducto) {
        super.setIdProducto(idProducto);
        updateProducto();
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    private void updateProducto() {
        //if(producto!=null)
        //  producto.getComprados().remove(id); //No Use

        //TODO DAO
        //compra = DAO compra . get( idCompra );

        //producto.getComprados().put(id, this); //No Use
    }

    @Override
    public int updateOnDb() {
        return 0;
    }

    @Override
    public int refreshFromDb() {
        return 0;
    }

    @Override
    public String insertString() {
        return IPersistible.buildInsertString(TABLE_NAME, COL_NAMES);
    }

    @Override
    public String updateString() {
        return IPersistible.buildUpdateString(TABLE_NAME, ID_COL_NAME, COL_NAMES, getId());
    }
}
