package app.data.casteldao.daomodel;

public abstract class Identifiable {

    protected static String ID_COL_NAME = "id";

    protected int id;

    protected Identifiable(int id) {
        this.id = Math.max(id, 0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = Math.max(id, 0);
    }

    protected String getIdColName() {
        return "id";
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " ID: " + getId();
    }
}
