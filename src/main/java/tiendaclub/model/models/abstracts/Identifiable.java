package tiendaclub.model.models.abstracts;

public abstract class Identifiable implements IPersistible {

    protected static String ID_COL_NAME = "id";

    protected int id;

    protected Identifiable(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
