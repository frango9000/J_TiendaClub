package app.model.models;

import app.model.IPersistible;
import app.model.models.abstracts.AbstractVendido;

public class Vendido extends AbstractVendido implements IPersistible {
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
}
