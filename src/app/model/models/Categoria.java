package app.model.models;

import app.model.IPersistible;
import app.model.models.abstracts.AbstractCategoria;

import java.util.HashMap;

public class Categoria extends AbstractCategoria implements IPersistible {
    private HashMap<Integer, Producto> productos = new HashMap<>();

    public Categoria(byte id, String nombre) {
        super(id, nombre);
    }

    public Categoria(String nombre) {
        super(nombre);
    }

    public HashMap<Integer, Producto> getProductos() {
        return productos;
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
