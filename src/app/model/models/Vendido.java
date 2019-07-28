package app.model.models;

import app.model.IPersistible;
import app.model.models.abstracts.AbstractVendido;

public class Vendido extends AbstractVendido implements IPersistible {
    public Vendido(int id, int idVenta, short idProducto, int cantidad, int precioUnidad) {
        super(id, idVenta, idProducto, cantidad, precioUnidad);
    }

    public Vendido(int idVenta, short idProducto, int cantidad, int precioUnidad) {
        super(idVenta, idProducto, cantidad, precioUnidad);
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
