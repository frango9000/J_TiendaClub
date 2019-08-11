package tiendaclub.model.models.abstracts;

public abstract class Activable extends Identifiable {
    protected boolean activo;

    protected Activable(int id, boolean activo) {
        super(id);
        this.activo = activo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void toggleActivo() {
        setActivo(!isActivo());
    }
}
