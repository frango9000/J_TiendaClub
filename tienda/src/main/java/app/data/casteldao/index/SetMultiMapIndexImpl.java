package app.data.casteldao.index;

import app.data.casteldao.GenericDao;
import app.data.casteldao.index.core.SetMultiMapIndex;
import app.data.casteldao.model.IEntity;
import java.util.function.Function;

public class SetMultiMapIndexImpl<K, E extends IEntity<Integer>> extends SetMultiMapIndex<K, E, Integer> {

    public SetMultiMapIndexImpl(GenericDao<Integer, E> dataSource, String indexColumnName, Function<E, K> keyValueFunction) {
        super(dataSource, indexColumnName, keyValueFunction);
    }
}
