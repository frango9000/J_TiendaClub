package app.model;

import app.data.casteldao.model.ActivableEntity;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public abstract class ActivablePropertyEntity extends ActivableEntity {

    protected BooleanProperty activeProperty;

    protected ActivablePropertyEntity(int id) {
        super(id);
        setActive(true);
    }

    public BooleanProperty activeProperty() {
        if (activeProperty == null) {
            activeProperty = new SimpleBooleanProperty(this, "active");
        }
        return activeProperty;
    }

    @Override
    public boolean isActive() {
        return activeProperty().get();
    }

    @Override
    public void setActive(boolean active) {
        activeProperty().set(active);
    }

    @Override
    public ActivablePropertyEntity clone() {
        ActivablePropertyEntity clone = (ActivablePropertyEntity) super.clone();
        clone.activeProperty = new SimpleBooleanProperty(this.isActive());
        return clone;
    }

}
