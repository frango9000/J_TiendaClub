package app.model.models;

import app.model.IPersistible;
import app.model.models.abstracts.AbstractAcceso;

import java.util.HashMap;

public class Acceso extends AbstractAcceso implements IPersistible {
    private HashMap<Integer, Usuario> usuarios = new HashMap<>();

    public Acceso(int id, String nivel) {
        super(id, nivel);
    }

    public Acceso(String nivel) {
        super(nivel);
    }

    public HashMap<Integer, Usuario> getUsuarios() {
        return usuarios;
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
