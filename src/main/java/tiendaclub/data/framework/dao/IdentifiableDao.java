package tiendaclub.data.framework.dao;

import tiendaclub.data.framework.index.IdUniqueIndex;
import tiendaclub.model.models.abstracts.Identifiable;

import java.util.ArrayList;
import java.util.HashMap;

public class IdentifiableDao<V extends Identifiable> extends PersistibleDao<V> {

    private IdUniqueIndex<V> idIndex = new IdUniqueIndex<>();

    public IdentifiableDao(String TABLE_NAME) {
        super(TABLE_NAME);
        indexes.add(idIndex);
    }

    public V get(int id) {
        if (id > 0) {
            if (idIndex.getIndexMap().containsKey(id)) {
                return idIndex.getIndexMap().get(id);
            } else {
                return query(id);
            }
        } else {
            return null;
        }
    }

    public ArrayList<V> getList(ArrayList<Integer> ids) {
        ArrayList<V> list = new ArrayList<>();
        if (ids.size() > 0) {
            ArrayList<Integer> idsToQuery = new ArrayList<>();
            for (int id : ids) {
                if (!idIndex.getIndexMap().containsKey(id)) {
                    idsToQuery.add(id);
                }
            }
            if (ids.size() > 0) {
                query(idsToQuery);
            }
            for (int id : ids) {
                list.add(idIndex.getIndexMap().get(id));
            }
        }
        return list;
    }

    public HashMap<Integer, V> get(ArrayList<Integer> ids) {
        HashMap<Integer, V> filteredHashMap = new HashMap<>();
        if (ids.size() > 0) {
            ArrayList<V> objs = getList(ids);
            for (V t : objs) {
                filteredHashMap.putIfAbsent(t.getId(), t);
            }
        }
        return filteredHashMap;
    }

    public HashMap<Integer, V> getCache() {
        return idIndex.getIndexMap();
    }
}
