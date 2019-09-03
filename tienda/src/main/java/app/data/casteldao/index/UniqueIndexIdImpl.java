package app.data.casteldao.index;

import app.data.casteldao.GenericDao;
import app.data.casteldao.index.core.UniqueIndexId;
import app.data.casteldao.model.AbstractEntity;
import app.data.casteldao.model.IEntity;
import app.data.casteldao.model.IIdentifiable;

public class UniqueIndexIdImpl<E extends IEntity<Integer>> extends UniqueIndexId<Integer, E, Integer> {


    public UniqueIndexIdImpl(GenericDao<Integer, E> dataSource) {
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
