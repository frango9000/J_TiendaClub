package casteldao.model;

public interface IActivable {

    boolean isActive();

    void setActive(boolean active);

    void toggleActive();

}
