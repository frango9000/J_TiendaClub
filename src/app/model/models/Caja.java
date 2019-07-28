package app.model.models;

import app.model.IPersistible;
import app.model.models.abstracts.AbstractCaja;

import java.util.HashMap;

public class Caja extends AbstractCaja implements IPersistible {
    private Sede sede;

    private HashMap<Integer, CierreZ> cierresZs = new HashMap<>();
    private HashMap<Integer, Venta> ventas = new HashMap<>();

    public Caja(int id, int idSede, String nombre) {
        super(id, idSede, nombre);
        updateSede();
    }

    public Caja(int idSede, String nombre) {
        super(idSede, nombre);
        updateSede();
    }

    @Override
    public void setIdSede(int idSede) {
        super.setIdSede(idSede);
        updateSede();
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    private void updateSede() {
        if (sede != null)
            sede.getCajas().remove(id);
        //TODO DAO
        //sede = DAO sede . get ( idSede );
        sede.getCajas().put(id, this);
    }

    public HashMap<Integer, CierreZ> getCierresZs() {
        return cierresZs;
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
