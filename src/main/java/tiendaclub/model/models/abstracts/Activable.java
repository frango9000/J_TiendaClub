package tiendaclub.model.models.abstracts;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public abstract class Activable extends Identifiable {
    protected BooleanProperty activo;

    public BooleanProperty activeProperty() {
        if (activo == null) activo = new SimpleBooleanProperty(this, "activo");
        return activo;
    }

    protected Activable(int id, boolean activo) {
        super(id);
        activeProperty().set(activo);
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
