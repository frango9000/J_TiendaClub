package tiendaclub.data.framework;

import tiendaclub.data.framework.dao.IndexIdDao;
import tiendaclub.model.models.Usuario;

public class UsuarioStringIndexDao extends IndexIdDao<Usuario> {

    public UsuarioStringIndexDao(String TABLE_NAME) {
        super(TABLE_NAME);
    }

//    HashMapIndex<String, Usuario> usernameIndex = new HashMapIndex<>() {
//        public void index(Usuario value){
//            index.computeIfAbsent(value.getUsername(), e -> new HashMap<>());
//            index.get(value.getUsername()).putIfAbsent(value.getId(), value);
//        }
//
//        public void deindex(Usuario objectT){
//            index.get(objectT.getUsername()).remove(objectT.getId());
//        }
//    };
//
//    public UsuarioStringIndexDao(String TABLE_NAME) {
//        super("usuarios");
//        indexes.add(usernameIndex);
//    }

}
