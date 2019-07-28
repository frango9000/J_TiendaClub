package app.model.models;

import app.model.IPersistible;
import app.model.models.abstracts.AbstractProducto;

public class Producto extends AbstractProducto implements IPersistible {
    private Categoria categoria;

    //private HashMap<Integer, Comprado> comprados = new HashMap<>(); //No Use

    public Producto(short id, String nombre, int precioVenta, int iva, byte idCategoria) {
        super(id, nombre, precioVenta, iva, idCategoria);
        updateCategoria();
    }

    public Producto(String nombre, int precioVenta, int iva, byte idCategoria) {
        super(nombre, precioVenta, iva, idCategoria);
        updateCategoria();
    }

    @Override
    public void setIdCategoria(byte idCategoria) {
        super.setIdCategoria(idCategoria);
        updateCategoria();
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void updateCategoria() {
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
