package tiendaclub.model.models.abstracts;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public abstract class Activable extends Persistible {

    protected BooleanProperty activo;

    protected Activable(int id) {
        super(id);
        setActivo(true);
    }

    public BooleanProperty activeProperty() {
        if (activo == null) {
            activo = new SimpleBooleanProperty(this, "activo");
        }
        return activo;
    }

    public boolean isActivo() {
        return activeProperty().get();
    }

    public void setActivo(boolean activo) {
        activeProperty().set(activo);
    }

    public void toggleActivo() {
        setActivo(!isActivo());
    }
}
