package tiendaclub.model.models;

import tiendaclub.data.DataStore;
import tiendaclub.data.framework.dao.PersistibleDao;
import tiendaclub.model.models.abstracts.AbstractComprado;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Comprado extends AbstractComprado {
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
        pst.setInt(1, getIdCompra());
        pst.setInt(2, getIdProducto());
        pst.setInt(3, getCantidad());
        pst.setInt(4, getPrecioUnidad());
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

    public void setCompra(Compra compra2) {
        if (compra != null)
            compra.getComprados().remove(getId());
        this.compra = compra2;
        if (compra != null)
            compra.getComprados().put(getId(), this);
    }

    public void updateCompra() {
        setCompra(DataStore.getCompras().get(getIdCompra()));
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

    public void updateProducto() {
        setProducto(DataStore.getProductos().get(getIdProducto()));
    }

    @Override
    public int insertIntoDB() {
        return 0;
    }

    @Override
    public int updateOnDb() {
        return DataStore.getComprados().update(this);
    }

    @Override
    public int refreshFromDb() {
        return DataStore.getComprados().updateObject(this);
    }

    @Override
    public int deleteFromDb() {
        return 0;
    }

    @Override
    public String getInsertString() {
        return PersistibleDao.buildInsertString(TABLE_NAME, COL_NAMES);
    }

    @Override
    public String getUpdateString() {
        return PersistibleDao.buildUpdateString(TABLE_NAME, ID_COL_NAME, COL_NAMES, getId());
    }
}
