package tiendaclub.model.models.abstracts;

public abstract class Identifiable {

    protected static String ID_COL_NAME = "id";

    protected int id;

    protected Identifiable(int id) {
        this.id = Math.max(id, 0);
    }

    public void setId(int id) {
        this.id = Math.max(id, 0);
    }

    public int getId() {
        return id;
    }

    protected String getIdColName() {
        return "id";
    }
}
