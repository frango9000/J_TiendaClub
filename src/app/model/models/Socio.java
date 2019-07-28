package app.model.models;

import app.model.IPersistible;
import app.model.models.abstracts.AbstractSocio;

import java.time.LocalDateTime;

public class Socio extends AbstractSocio implements IPersistible {
    public Socio(short id, String dni, String nombre, LocalDateTime fechaIn, LocalDateTime fechaActive, LocalDateTime fechaInactive) {
        super(id, dni, nombre, fechaIn, fechaActive, fechaInactive);
    }

    public Socio(String dni, String nombre) {
        super(dni, nombre);
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
