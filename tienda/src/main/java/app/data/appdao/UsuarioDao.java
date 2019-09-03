package app.data.appdao;

import app.data.casteldao.dao.IndexIdActiveDao;
import app.data.casteldao.index.SetMultiMapIndexImpl;
import app.data.casteldao.index.core.SimpleMapIndex;
import app.model.Acceso;
import app.model.Usuario;

public class UsuarioDao extends IndexIdActiveDao<Usuario> {

    private SimpleMapIndex<String, Usuario> usernameIndex = new SimpleMapIndex<>(getDao(), "username", Usuario::getUsername);
    private SetMultiMapIndexImpl<Acceso, Usuario> indexAcceso = new SetMultiMapIndexImpl<>(getDao(), "idAcceso", Usuario::getAcceso);

    public UsuarioDao() {
        super(Usuario.TABLE_NAME, Usuario.class);
        indexes.add(indexAcceso);
        indexes.add(usernameIndex);
    }

    public SetMultiMapIndexImpl<Acceso, Usuario> getIndexAcceso() {
        return indexAcceso;
    }

    public SimpleMapIndex<String, Usuario> getUsernameIndex() {
        return usernameIndex;
    }
}
