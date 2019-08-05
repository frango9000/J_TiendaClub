package tiendaclub.model;

import java.util.ArrayList;
import java.util.HashMap;

public interface IDao<T> extends Globals {
    T query(int id);

    T query(String colName, String unique);

    T query(String col1Name, String uni, String col2Name, String que);

    HashMap<Integer, T> query(ArrayList<Integer> ids);

//    HashMap<Integer, T> query(String colName, ArrayList<String> search);

    HashMap<Integer, T> queryAll();

    T get(int id);

    HashMap<Integer, T> get(ArrayList<Integer> ids);

    ArrayList<T> getList(ArrayList<Integer> ids);

    HashMap<Integer, T> getCache();

    int insert(T objecT);

    int update(T objecT);

    int updateObject(T objectT);

    int delete(T objecT);

    int delete(int id);

    int deleteSome(ArrayList<T> toDelete);

    int deleteSomeIds(ArrayList<Integer> toDelete);
}
