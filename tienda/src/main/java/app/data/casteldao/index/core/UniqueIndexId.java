package app.data.casteldao.index.core;

import app.data.casteldao.GenericDao;
import app.data.casteldao.model.IEntity;
import java.io.Serializable;
import java.util.function.Function;

public abstract class UniqueIndexId<K, E extends IEntity<I>, I extends Serializable> extends SimpleMapIndex<K, E, I> {


    public UniqueIndexId(GenericDao<I, E> dataSource, String indexColumnName, Function<E, K> keyValueFunction) {
        super(dataSource, indexColumnName, keyValueFunction);
    }


    @Override
    public void reindex(E entity) {
        deindex(entity);
        index(entity);
    }

    @Override
    public void deindex(E entity) {
        this.deindex(entity.getId());
    }
}
