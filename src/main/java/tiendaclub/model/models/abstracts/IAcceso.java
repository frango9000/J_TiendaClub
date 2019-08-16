package tiendaclub.model.models.abstracts;

import tiendaclub.model.models.Acceso;

public interface IAcceso extends IPersistible {

    int getIdAcceso();

    void setIdAcceso(int idAcceso);

    Acceso getAcceso();

    void setAcceso(Acceso acceso);

}
