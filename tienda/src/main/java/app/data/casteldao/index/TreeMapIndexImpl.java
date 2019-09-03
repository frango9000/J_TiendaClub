package app.data.casteldao.index;

import app.data.casteldao.GenericDao;
import app.data.casteldao.index.core.SimpleTreeMapIndex;
import app.data.casteldao.model.IEntity;
import java.util.function.Function;

public class TreeMapIndexImpl<K extends Comparable, E extends IEntity<Integer>> extends SimpleTreeMapIndex<K, E, Integer> {

    public TreeMapIndexImpl(GenericDao<Integer, E> dataSource, String indexColumnName, Function<E, K> keyValueFunction) {
        super(dataSource, indexColumnName, keyValueFunction);
    }
}
