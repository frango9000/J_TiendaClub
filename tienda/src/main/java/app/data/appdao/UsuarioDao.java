package app.data.appdao;

import app.model.Acceso;
import app.model.Usuario;
import casteldao.dao.DataSourceIdActive;
import casteldao.index.SetMultiMapIndexEntityImpl;
import casteldao.index.SimpleUniqueIndexStringImpl;

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
