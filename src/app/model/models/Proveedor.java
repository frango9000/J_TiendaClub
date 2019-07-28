package app.model.models;

import app.model.IPersistible;
import app.model.models.abstracts.AbstractProveedor;

import java.util.HashMap;

public class Proveedor extends AbstractProveedor implements IPersistible {
    private HashMap<Integer, Compra> compras = new HashMap<>();

    public Proveedor(int id, String nif, String nombre) {
        super(id, nif, nombre);
    }

    public Proveedor(String nif, String nombre) {
        super(nif, nombre);
    }

    public HashMap<Integer, Compra> getCompras() {
        return compras;
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
