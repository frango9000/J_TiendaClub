package app.model.models;

import app.model.IPersistible;
import app.model.models.abstracts.AbstractUsuario;

import java.util.HashMap;

public class Usuario extends AbstractUsuario implements IPersistible {
    private Acceso acceso;

    private HashMap<Integer, Compra> compras = new HashMap<>();
    private HashMap<Integer, Venta> ventas = new HashMap<>();
    private HashMap<Integer, Transferencia> transferencias = new HashMap<>();

    public Usuario(int id, String user, String pass, String nombre, int idAcceso) {
        super(id, user, pass, nombre, idAcceso);
        updateAcceso();
    }

    public Usuario(String user, String pass, String nombre, int idAcceso) {
        super(user, pass, nombre, idAcceso);
        updateAcceso();
    }

    @Override
    public void setIdAcceso(int idAcceso) {
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
        if (acceso != null)
            acceso.getUsuarios().remove(id);
        //TODO DAO
        //usuario = DAO usuario . get ( idUsuario );
        acceso.getUsuarios().put(id, this);
    }

    public HashMap<Integer, Compra> getCompras() {
        return compras;
    }

    public HashMap<Integer, Venta> getVentas() {
        return ventas;
    }

    public HashMap<Integer, Transferencia> getTransferencias() {
        return transferencias;
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
