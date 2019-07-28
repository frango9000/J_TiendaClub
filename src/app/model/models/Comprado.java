package app.model.models;

import app.model.IPersistible;
import app.model.models.abstracts.AbstractComprado;

public class Comprado extends AbstractComprado implements IPersistible {
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
}
