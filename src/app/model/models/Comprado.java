package app.model.models;

import app.model.IPersistible;
import app.model.models.abstracts.AbstractComprado;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Comprado extends AbstractComprado implements IPersistible {
    public static final String TABLE_NAME = "comprados";
    private static final ArrayList<String> COL_NAMES = new ArrayList<>(Arrays.asList("idCompra", "idProducto", "cantidad", "precio_unidad"));

    private Compra compra;
    private Producto producto;

    public Comprado(int id, int idCompra, int idProducto, int cantidad, int precioUnidad) {
        super(id, idCompra, idProducto, cantidad, precioUnidad);
        updateCompra();
        updateProducto();
    }

    public Comprado(int idCompra, int idProducto, int cantidad, int precioUnidad) {
        super(idCompra, idProducto, cantidad, precioUnidad);
        updateCompra();
        updateProducto();
    }

    public Comprado(ResultSet rs) throws SQLException {
        this(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
    }

    @Override
    public void buildStatement(PreparedStatement pst) throws SQLException {
        pst.setInt(1, idCompra);
        pst.setInt(2, idProducto);
        pst.setInt(3, cantidad);
        pst.setInt(4, precioUnidad);
    }

    @Override
    public void updateObject(ResultSet rs) throws SQLException {
        //setId(rs.getInt(1));
        setIdCompra(rs.getInt(2));
        setIdProducto(rs.getInt(3));
        setCantidad(rs.getInt(4));
        setPrecioUnidad(rs.getInt(5));
    }

    @Override
    public void setIdCompra(int idCompra) {
        super.setIdCompra(idCompra);
        updateCompra();
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public void updateCompra() {
        if (compra != null)
            compra.getComprados().remove(id);
        //TODO DAO
        //compra = DAO compra . get( idCompra );
        compra.getComprados().put(id, this);
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

    public void updateProducto() {
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
