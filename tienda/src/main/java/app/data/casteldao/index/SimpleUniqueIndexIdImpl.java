package app.data.casteldao.index;

import app.data.casteldao.GenericDao;
import app.data.casteldao.index.core.SimpleMapIndex;
import app.data.casteldao.model.AbstractEntity;
import app.data.casteldao.model.IEntity;
import app.data.casteldao.model.IIdentifiable;

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
