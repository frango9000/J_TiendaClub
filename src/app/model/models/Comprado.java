package app.model.models;

import app.model.IPersistible;
import app.model.models.abstracts.AbstractComprado;

public class Comprado extends AbstractComprado implements IPersistible {

    public Comprado(int id, int idCompra, short idProducto, int cantidad, int precioUnidad) {
        super(id, idCompra, idProducto, cantidad, precioUnidad);
    }

    public Comprado(int idCompra, short idProducto, int cantidad, int precioUnidad) {
        super(idCompra, idProducto, cantidad, precioUnidad);
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
