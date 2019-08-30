package tiendaclub.data;

import tiendaclub.model.Caja;
import tiendaclub.model.Sede;
import tiendaclub.model.Usuario;

public class SessionStore {

    static Caja caja;
    static Sede sede;
    static Usuario usuario;

    public static Caja getCaja() {
        return caja;
    }

    public static void setCaja(Caja caja) {
        SessionStore.caja = caja;
    }

    public static Sede getSede() {
        return sede;
    }

    public static void setSede(Sede sede) {
        SessionStore.sede = sede;
    }

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        SessionStore.usuario = usuario;
    }
}
