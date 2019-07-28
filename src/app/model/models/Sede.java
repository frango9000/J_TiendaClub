package app.model.models;

import app.model.IPersistible;
import app.model.models.abstracts.AbstractSede;

import java.util.HashMap;

public class Sede extends AbstractSede implements IPersistible {
    private HashMap<Integer, Caja> cajas = new HashMap<>();
    private HashMap<Integer, Compra> compras = new HashMap<>();

    public Sede(byte id, String nombre, String telefono, String direccion) {
        super(id, nombre, telefono, direccion);
    }

    public Sede(String nombre, String telefono, String direccion) {
        super(nombre, telefono, direccion);
    }

    public HashMap<Integer, Caja> getCajas() {
        return cajas;
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
