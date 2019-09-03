package casteldao.index;

import casteldao.GenericDao;
import casteldao.index.core.SimpleMapIndex;
import casteldao.model.IEntity;
import java.util.function.Function;

public class SimpleUniqueIndexStringImpl<E extends IEntity<Integer>> extends SimpleMapIndex<String, E, Integer> {


    public SimpleUniqueIndexStringImpl(GenericDao<Integer, E> dataSource, String indexColumnName, Function<E, String> keyValueFunction) {
        super(dataSource, indexColumnName, keyValueFunction);
    }


}
