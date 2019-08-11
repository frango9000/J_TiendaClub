package tiendaclub.data;


import tiendaclub.model.models.abstracts.Activable;

import java.util.HashMap;

public class ActivableDao<T extends Activable> extends PersistableDao<T> {

    protected final String BOOL_COL_NAME = "activo";

    private final HashMap<Boolean, HashMap<Integer, T>> activeTable = new HashMap<>();

    public ActivableDao(String TABLE_NAME) {
        super(TABLE_NAME);
        activeTable.put(true, new HashMap<Integer, T>());
        activeTable.put(false, new HashMap<Integer, T>());
    }

    @Override
    protected void index(T objectT) {
        super.index(objectT);
        activeTable.get(!objectT.isActivo()).remove(objectT.getId());
        activeTable.get(objectT.isActivo()).putIfAbsent(objectT.getId(), objectT);
    }

    @Override
    protected void deindex(int id) {
        super.deindex(id);
        activeTable.get(true).remove(id);
        activeTable.get(false).remove(id);
    }

    @Override
    protected void deindex(T objectT) {
        super.deindex(objectT);
        activeTable.get(objectT.isActivo()).remove(objectT.getId());
    }

    @Override
    protected void reindex(T objectT) {
        super.reindex(objectT);
        activeTable.get(!objectT.isActivo()).remove(objectT.getId());
        activeTable.get(objectT.isActivo()).putIfAbsent(objectT.getId(), objectT);
    }

    public HashMap<Integer, T> getInactivesTable() {
        return activeTable.get(false);
    }

    public HashMap<Integer, T> getActivesTable() {
        return activeTable.get(true);
    }
}
