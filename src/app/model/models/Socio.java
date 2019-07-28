package app.model.models;

import app.model.IPersistible;
import app.model.models.abstracts.AbstractSocio;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Socio extends AbstractSocio implements IPersistible {
    private HashMap<Integer, Venta> ventas = new HashMap<>();

    public Socio(int id, String dni, String nombre, LocalDateTime fechaIn, LocalDateTime fechaActive, LocalDateTime fechaInactive) {
        super(id, dni, nombre, fechaIn, fechaActive, fechaInactive);
    }

    public Socio(String dni, String nombre) {
        super(dni, nombre);
    }

    public HashMap<Integer, Venta> getVentas() {
        return ventas;
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
