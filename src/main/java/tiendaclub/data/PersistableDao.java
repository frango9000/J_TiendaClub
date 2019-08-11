package tiendaclub.data;

import tiendaclub.model.models.abstracts.Identifiable;

import java.util.ArrayList;
import java.util.HashMap;

public class PersistableDao<T extends Identifiable> extends GenericDao<T> implements IPersistibleDao<T> {

    protected final HashMap<Integer, T> table = new HashMap<>();

    public PersistableDao(String TABLE_NAME) {
        super(TABLE_NAME);
    }

    @Override
    public T get(int id) {
        if (id > 0) {
            if (table.containsKey(id)) {
                return table.get(id);
            } else {
                return query(id);
            }
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<T> getList(ArrayList<Integer> ids) {
        ArrayList<T> list = new ArrayList<>();
        if (ids.size() > 0) {
            ArrayList<Integer> idsToQuery = new ArrayList<>();
            for (int id : ids) {
                if (!table.containsKey(id)) {
                    idsToQuery.add(id);
                }
            }
            if (ids.size() > 0) {
                query(idsToQuery);
            }
            for (int id : ids) {
                list.add(table.get(id));
            }
        }
        return list;
    }

    @Override
    public HashMap<Integer, T> get(ArrayList<Integer> ids) {
        HashMap<Integer, T> filteredHashMap = new HashMap<>();
        if (ids.size() > 0) {
            ArrayList<T> objs = getList(ids);
            for (T t : objs) {
                filteredHashMap.putIfAbsent(t.getId(), t);
            }
        }
        return filteredHashMap;
    }

    @Override
    public HashMap<Integer, T> getCache() {
        return table;
    }

    @Override
    protected void index(T objectT) {
        super.index(objectT);
        table.putIfAbsent(objectT.getId(), objectT);
    }

    @Override
    protected void deindex(int id) {
        super.deindex(id);
        table.remove(id);
    }

    @Override
    protected void deindex(T objectT) {
        super.deindex(objectT);
        table.remove(objectT.getId(), objectT);
    }

    @Override
    protected void reindex(T objectT) {
        super.reindex(objectT);
    }
}
