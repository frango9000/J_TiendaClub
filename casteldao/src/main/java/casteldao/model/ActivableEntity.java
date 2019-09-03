package casteldao.model;

public abstract class ActivableEntity extends EntityInt implements IActivable {

    private boolean active;

    public ActivableEntity() {
        super(0);
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
