package casteldao.index;

import casteldao.GenericDao;
import casteldao.index.core.SetMultiMapIndex;
import casteldao.model.IEntity;
import java.util.function.Function;

public class SetMultiMapIndexImpl<K, E extends IEntity<Integer>> extends SetMultiMapIndex<K, E, Integer> {

    public SetMultiMapIndexImpl(GenericDao<Integer, E> dataSource, String indexColumnName, Function<E, K> keyValueFunction) {
        super(dataSource, indexColumnName, keyValueFunction);
    }
}
