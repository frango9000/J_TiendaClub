package tiendaclub.model.models.abstracts;

import tiendaclub.model.IIdentifiable;

public class AbstractIdentifiable implements IIdentifiable {
    protected int id;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
