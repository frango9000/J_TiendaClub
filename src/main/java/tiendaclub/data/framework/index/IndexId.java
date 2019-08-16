package tiendaclub.data.framework.index;

import tiendaclub.data.framework.datasource.DataSource;
import tiendaclub.data.framework.index.model.SimpleMapIndex;
import tiendaclub.model.models.abstracts.Persistible;

public class IndexId<V extends Persistible> extends SimpleMapIndex<Integer, V> {


    public IndexId(DataSource<V> dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Integer indexKey(V value) {
        return value.getId();
    }

    @Override
    public void deindex(int id) {
        index.remove(id);
    }


}
