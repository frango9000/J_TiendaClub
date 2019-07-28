package app.model.models;

import app.model.IPersistible;
import app.model.models.abstracts.AbstractUsuario;

import java.util.HashMap;

public class Usuario extends AbstractUsuario implements IPersistible {
    private Acceso acceso;

    private HashMap<Integer, Compra> compras = new HashMap<>();
    private HashMap<Integer, Venta> ventas = new HashMap<>();

    public Usuario(byte id, String user, String pass, String nombre, byte idAcceso) {
        super(id, user, pass, nombre, idAcceso);
        updateAcceso();
    }

    public Usuario(String user, String pass, String nombre, byte idAcceso) {
        super(user, pass, nombre, idAcceso);
        updateAcceso();
    }

    @Override
    public void setIdAcceso(byte idAcceso) {
        super.setIdAcceso(idAcceso);
        updateAcceso();
    }

    public Acceso getAcceso() {
        return acceso;
    }

    public void setAcceso(Acceso acceso) {
        this.acceso = acceso;
    }

    private void updateAcceso() {
        //TODO DAO
    }

    public HashMap<Integer, Compra> getCompras() {
        return compras;
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
