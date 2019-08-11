package tiendaclub.data;

import java.util.ArrayList;
import java.util.HashMap;

public interface IPersistibleDao<T> {

    T get(int id);

    HashMap<Integer, T> get(ArrayList<Integer> ids);

    ArrayList<T> getList(ArrayList<Integer> ids);

    HashMap<Integer, T> getCache();

}
