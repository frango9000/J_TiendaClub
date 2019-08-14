package tiendaclub.data.framework.index;

import tiendaclub.data.framework.datasource.DataSource;
import tiendaclub.model.models.abstracts.Persistible;

import java.util.ArrayList;
import java.util.HashMap;

public class SimpleIndexId<V extends Persistible> extends AbstractIndex<Integer, V> {

    protected HashMap<Integer, V> index = new HashMap<Integer, V>();

    public SimpleIndexId(DataSource<V> dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void index(V objectV) {
        index.putIfAbsent(objectV.getId(), objectV);
    }

    @Override
    public void deindex(int id) {
        index.remove(id);
    }

    @Override
    public void deindex(V objectT) {
        deindex(objectT.getId());
    }

    @Override
    public void reindex(V objectT) {
        deindex(objectT);
        index(objectT);
    }
//
//    @Override
//    private HashMap<Integer, V> getIndexMap(Integer key) {
//        return getIndexMap();
//    }
//
//    @Override
//    private HashMap<Integer, V> getIndexMap() {
//        return index;
//    }
//
//    @Override
//    public Collection<V> getIndexValues() {
//        return index.values();
//    }
//
//    @Override
//    public Collection<V> getIndexValues(Integer key) {
//        return Collections.singletonList(index.get(key));
//    }


    public V getMap(int id) {
        if (id > 0) {
            if (cacheContains(id)) {
                return getFromCache(id);
            } else {
                return dataSource.query(id);
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
                if (!cacheContains(id)) {
                    idsToQuery.add(id);
                }
            }
            if (ids.size() > 0) {
                dataSource.querySome(idsToQuery);
            }
            for (int id : ids) {
                list.add(getFromCache(id));
            }
        }
        return list;
    }

    public HashMap<Integer, V> getMap(ArrayList<Integer> ids) {
        HashMap<Integer, V> filteredHashMap = new HashMap<>();
        if (ids.size() > 0) {
            ArrayList<V> objs = getList(ids);
            for (V t : objs) {
                filteredHashMap.putIfAbsent(t.getId(), t);
            }
        }
        return filteredHashMap;
    }

    public boolean cacheContains(int id) {
        return index.containsKey(id);
    }

    public V getFromCache(int id) {
        return index.get(id);
    }

    public HashMap<Integer, V> getAllCache() {
        return index;
    }


}
