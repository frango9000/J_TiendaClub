package tiendaclub;

import tiendaclub.control.PropsLoader;
import tiendaclub.data.DataStore;

public class TestFX {

    public static void main(String[] args) {

        PropsLoader.loadProps();

        DataStore.firstQuery();
        DataStore.getUsuarios().getIndexAcceso().getValue(1);
        DataStore.getUsuarios().getIndexAcceso().getValue(3);

    }
}
