package app.data.casteldao.index;

import app.data.casteldao.GenericDao;
import app.data.casteldao.index.core.SetMultiMapIndex;
import app.data.casteldao.index.core.maps.SetIndexMultimap;
import app.data.casteldao.model.ActivableEntity;
import java.util.Set;

public class MultiIndexActive<E extends ActivableEntity> extends SetMultiMapIndex<Boolean, E, Integer> {

    public MultiIndexActive(GenericDao<Integer, E> dataSource) {
        super(dataSource, "active", ActivableEntity::isActive);
        this.index = new SetIndexMultimap<>(2);
    }

    @Override
    public Boolean indexKey(E entity) {
        return entity.isActive();
    }

    @Override
    public void reindex(E entity) {
        index.remove(!entity.isActive(), entity);
        index(entity);
    }

    public Set<E> getActive(boolean active) {
        return getKeyValues(active);
    }

    public Set<E> getActiveCache(boolean active) {
        return getCacheKeyValues(active);
    }
}
