package app.data.appdao;

import app.data.casteldao.dao.DataSourceIdActive;
import app.data.casteldao.index.SetMultiMapIndexEntityImpl;
import app.data.casteldao.index.SimpleUniqueIndexStringImpl;
import app.model.Acceso;
import app.model.Usuario;

public class UsuarioDao extends DataSourceIdActive<Usuario> {

    private SimpleUniqueIndexStringImpl<Usuario> usernameIndex = new SimpleUniqueIndexStringImpl<>(getDao(), "username", Usuario::getUsername);
    private SetMultiMapIndexEntityImpl<Acceso, Usuario> indexAcceso = new SetMultiMapIndexEntityImpl<>(getDao(), "idAcceso", Usuario::getIdAcceso);

    public UsuarioDao() {
        super(Usuario.TABLE_NAME, Usuario.class);
        indexes.add(indexAcceso);
        indexes.add(usernameIndex);
    }

    public SetMultiMapIndexEntityImpl<Acceso, Usuario> getIndexAcceso() {
        return indexAcceso;
    }

    public SimpleUniqueIndexStringImpl<Usuario> getUsernameIndex() {
        return usernameIndex;
    }
}
