package app.data.appdao;

import app.data.casteldao.daomodel.IndexIdActiveDao;
import app.data.casteldao.index.MultiIndexPersistible;
import app.data.casteldao.index.core.SimpleMapIndex;
import app.model.Acceso;
import app.model.Usuario;

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
