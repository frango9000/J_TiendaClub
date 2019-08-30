package tiendaclub.model.models.core;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import tiendaclub.data.framework.model.Persistible;

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

    @Override
    public Object clone() throws CloneNotSupportedException {
        Activable clone = (Activable) super.clone();
        clone.activo = new SimpleBooleanProperty(this.isActivo());
        return clone;
    }
}
