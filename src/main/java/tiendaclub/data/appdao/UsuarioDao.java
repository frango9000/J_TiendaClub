package tiendaclub.data.appdao;

import tiendaclub.data.casteldao.daomodel.IndexIdActiveDao;
import tiendaclub.data.casteldao.index.MultiIndexPersistible;
import tiendaclub.data.casteldao.index.core.SimpleMapIndex;
import tiendaclub.model.Acceso;
import tiendaclub.model.Usuario;

public class UsuarioDao extends IndexIdActiveDao<Usuario> {

    private SimpleMapIndex<String, Usuario> usernameIndex = new SimpleMapIndex<String, Usuario>(getDataSource(), "username", Usuario::getUsername);
    private MultiIndexPersistible<Acceso, Usuario> indexAcceso = new MultiIndexPersistible<>(getDataSource(), "idAcceso", Usuario::getIdAcceso);

    public UsuarioDao() {
        super(Usuario.TABLE_NAME, Usuario.class);
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
