package casteldao.index;

import casteldao.GenericDao;
import casteldao.index.core.SimpleTreeMapIndex;
import casteldao.model.IEntity;
import java.util.function.Function;

public class SimpleTreeMapIndexImpl<K extends Comparable, E extends IEntity<Integer>> extends SimpleTreeMapIndex<K, E, Integer> {

    public SimpleTreeMapIndexImpl(GenericDao<Integer, E> dataSource, String indexColumnName, Function<E, K> keyValueFunction) {
        super(dataSource, indexColumnName, keyValueFunction);
    }
}
