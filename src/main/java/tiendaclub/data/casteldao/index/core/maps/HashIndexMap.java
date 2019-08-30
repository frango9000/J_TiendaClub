package tiendaclub.data.casteldao.index.core.maps;


import com.google.common.collect.Maps;

public class HashIndexMap<K, V> extends AbstractIndexMap<K, V> {

    public HashIndexMap() {
        this.map = Maps.newHashMap();
    }

}
