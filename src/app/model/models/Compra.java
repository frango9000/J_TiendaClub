package app.model.models;

import app.model.IPersistible;
import app.model.models.abstracts.AbstractCompra;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Compra extends AbstractCompra implements IPersistible {
    private Usuario usuario;
    private Proveedor proveedor;
    private Sede sede;

    private HashMap<Integer, Comprado> comprados = new HashMap<>();

    public Compra(int id, int idUsuario, int idSede, int idProveedor, LocalDateTime fechahora) {
        super(id, idUsuario, idSede, idProveedor, fechahora);
        updateUsuario();
        updateProveedor();
        updateSede();
    }

    public Compra(int idUsuario, int idSede, int idProveedor, LocalDateTime fechahora) {
        super(idUsuario, idSede, idProveedor, fechahora);
        updateUsuario();
        updateProveedor();
        updateSede();
    }

    @Override
    public void setIdUsuario(int idUsuario) {
        super.setIdUsuario(idUsuario);
        updateUsuario();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    private void updateUsuario() {
        if (usuario != null)
            usuario.getCompras().remove(id);
        //TODO DAO
        //usuario = DAO usuario . get ( idUsuario );
        usuario.getCompras().put(id, this);
    }

    @Override
    public void setIdProveedor(int idProveedor) {
        super.setIdProveedor(idProveedor);
        updateProveedor();
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    private void updateProveedor() {
        if (proveedor != null)
            proveedor.getCompras().remove(id);
        //TODO DAO
        //proveedor = DAO proveedor . get ( idProveedor );
        proveedor.getCompras().put(id, this);
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
            sede.getCompras().remove(id);
        //TODO DAO
        //sede = DAO sede . get( idSede );
        sede.getCompras().put(id, this);
    }

    public HashMap<Integer, Comprado> getComprados() {
        return comprados;
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
