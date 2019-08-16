package tiendaclub.data.framework.index;

import tiendaclub.data.framework.datasource.DataSource;
import tiendaclub.model.models.abstracts.IAcceso;

public class IndexAcceso<V extends IAcceso> extends IndexPersistible<V> {

    public IndexAcceso(DataSource<V> dataSource) {
        super(dataSource);
        this.INDEX_COL_NAME = "idAcceso";
    }


    @Override
    public Integer indexKey(V value) {
        return value.getIdAcceso();
    }

}
