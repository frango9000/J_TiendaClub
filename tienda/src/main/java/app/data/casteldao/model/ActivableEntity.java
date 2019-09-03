package app.data.casteldao.model;

public abstract class ActivableEntity extends EntityInt implements IActivable {

    protected boolean active;

    public ActivableEntity() {
    }

    protected ActivableEntity(Integer id) {
        super(id);
        setActive(true);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void toggleActive() {
        setActive(!isActive());
    }

}
