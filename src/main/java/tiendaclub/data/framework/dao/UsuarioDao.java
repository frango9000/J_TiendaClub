package tiendaclub.data.framework.dao;

import tiendaclub.data.framework.index.IndexAcceso;
import tiendaclub.model.models.Usuario;

public class UsuarioDao extends IndexIdActiveDao<Usuario> {

    private IndexAcceso<Usuario> indexAcceso = new IndexAcceso<>(dataSource);

    public UsuarioDao(String TABLE_NAME) {
        super(TABLE_NAME);
        indexes.add(indexAcceso);
    }

    public IndexAcceso<Usuario> getIndexAcceso() {
        return indexAcceso;
    }
}
