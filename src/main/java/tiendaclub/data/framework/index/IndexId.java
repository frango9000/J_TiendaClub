package tiendaclub.data.framework.index;

import tiendaclub.data.framework.datasource.DataSource;
import tiendaclub.data.framework.index.model.SimpleMapIndex;
import tiendaclub.model.models.abstracts.IPersistible;

public class IndexId<V extends IPersistible> extends SimpleMapIndex<Integer, V> {


    public IndexId(DataSource<V> dataSource) {
        this.dataSource = dataSource;
        this.INDEX_COL_NAME = "id";
    }

    @Override
    public Integer indexKey(V value) {
        return value.getId();
    }

    @Override
    public void deindex(int idValue) {
        index.remove(idValue);
    }


}
