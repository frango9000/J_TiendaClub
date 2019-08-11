package tiendaclub.data;

import tiendaclub.model.Globals;

import java.util.ArrayList;
import java.util.HashMap;

public interface IGenericDao<T> extends Globals {
    T query(int id);

    T query(String colName, String unique);

    T query(String col1Name, String uni, String col2Name, String que);

    HashMap<Integer, T> query(ArrayList<Integer> ids);

//    HashMap<Integer, T> query(String colName, ArrayList<String> search);

    HashMap<Integer, T> queryAll();

    int insert(T objecT);

    int update(T objecT);

    int updateObject(T objectT);

    int delete(T objecT);

    int delete(int id);

    int deleteSome(ArrayList<T> toDelete);

    int deleteSomeIds(ArrayList<Integer> toDelete);


}
