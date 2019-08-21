package tiendaclub.data.framework.dao;

import tiendaclub.data.framework.dao.core.IndexIdActiveDao;
import tiendaclub.data.framework.index.MultiIndexPersistible;
import tiendaclub.data.framework.index.core.SimpleMapIndex;
import tiendaclub.model.models.Acceso;
import tiendaclub.model.models.Usuario;

public class UsuarioDao extends IndexIdActiveDao<Usuario> {

    private SimpleMapIndex<String, Usuario> usernameIndex = new SimpleMapIndex<String, Usuario>(getDataSource(), "username", Usuario::getUsername);
    private MultiIndexPersistible<Acceso, Usuario> indexAcceso = new MultiIndexPersistible<>(getDataSource(), "idAcceso", Usuario::getIdAcceso);

    public UsuarioDao() {
        super(Usuario.TABLE_NAME);
        indexes.add(indexAcceso);
        indexes.add(usernameIndex);
    }

    public MultiIndexPersistible<Acceso, Usuario> getIndexAcceso() {
        return indexAcceso;
    }

    public SimpleMapIndex<String, Usuario> getUsernameIndex() {
        return usernameIndex;
    }
}
