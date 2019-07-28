package app.model.models;

import app.model.IPersistible;
import app.model.models.abstracts.AbstractProducto;

public class Producto extends AbstractProducto implements IPersistible {
    private Categoria categoria;

    //private HashMap<Integer, Comprado> comprados = new HashMap<>(); //No Use
    //private HashMap<Integer, Transferencia> transferencias = new HashMap<>(); //No Use

    public Producto(int id, String nombre, int precioVenta, int iva, int idCategoria) {
        super(id, nombre, precioVenta, iva, idCategoria);
        updateCategoria();
    }

    public Producto(String nombre, int precioVenta, int iva, int idCategoria) {
        super(nombre, precioVenta, iva, idCategoria);
        updateCategoria();
    }

    @Override
    public void setIdCategoria(int idCategoria) {
        super.setIdCategoria(idCategoria);
        updateCategoria();
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    private void updateCategoria() {
        if (categoria != null)
            categoria.getProductos().remove(id);
        //TODO DAO
        //categoria = DAO categoria . get ( idCategoria );
        categoria.getProductos().put(id, this);
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
