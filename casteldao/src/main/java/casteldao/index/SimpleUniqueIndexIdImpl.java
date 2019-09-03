package casteldao.index;

import casteldao.GenericDao;
import casteldao.index.core.SimpleMapIndex;
import casteldao.model.AbstractEntity;
import casteldao.model.IEntity;
import casteldao.model.IIdentifiable;

public class SimpleUniqueIndexIdImpl<E extends IEntity<Integer>> extends SimpleMapIndex<Integer, E, Integer> {


    public SimpleUniqueIndexIdImpl(GenericDao<Integer, E> dataSource) {
        super(dataSource, AbstractEntity.ID_COL_NAME, IIdentifiable::getId);
    }

    @Override
    public Integer indexKey(E entity) {
        return entity.getId();
    }


    @Override
    public void deindex(Integer idValue) {
        index.remove(idValue);
    }


}
