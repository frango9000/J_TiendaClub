package app.model.models;

import app.model.IPersistible;
import app.model.models.abstracts.AbstractCaja;

import java.util.HashMap;

public class Caja extends AbstractCaja implements IPersistible {
    private Sede sede;

    private HashMap<Integer, CierreZ> cierresZs = new HashMap<>();

    public Caja(short id, byte idSede, String nombre) {
        super(id, idSede, nombre);
        updateSede();
    }

    public Caja(byte idSede, String nombre) {
        super(idSede, nombre);
        updateSede();
    }

    @Override
    public void setIdSede(byte idSede) {
        super.setIdSede(idSede);
        updateSede();
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public HashMap<Integer, CierreZ> getCierresZs() {
        return cierresZs;
    }

    private void updateSede() {
        //TODO DAO
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
