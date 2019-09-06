package casteldao.index.core;

import casteldao.GenericDao;
import casteldao.index.core.maps.TreeIndexMap;
import casteldao.model.IEntity;
import com.google.common.collect.Sets;
import java.io.Serializable;
import java.util.Set;
import java.util.function.Function;

public class SimpleTreeMapIndex<K extends Comparable, E extends IEntity<I>, I extends Serializable> extends SimpleMapIndex<K, E, I> {

    public SimpleTreeMapIndex(GenericDao<I, E> dataSource, String indexColumnName, Function<E, K> keyValueFunction) {
        super(dataSource, indexColumnName, keyValueFunction);
        this.index = new TreeIndexMap<K, E>();
    }

    public TreeIndexMap<K, E> treeMap() {
        return (TreeIndexMap<K, E>) index;
    }

    public Set<E> getKeyLesserThanValues(K lesserThanThis, boolean inclusive) {
        dataSource.queryGreaterLesser(indexColumnName, lesserThanThis.toString(), false, inclusive);
        return getCacheKeyLesserThanValues(lesserThanThis, inclusive);
    }

    public Set<E> getKeyGreaterThanValues(K lesserThanThis, boolean inclusive) {
        dataSource.queryGreaterLesser(indexColumnName, lesserThanThis.toString(), true, inclusive);
        return getCacheKeyGreaterThanValues(lesserThanThis, inclusive);
    }

    public Set<E> getKeyIntervalValues(K start, boolean fromInclusive, K end, boolean toInclusive) {
        dataSource.queryBetween(indexColumnName, start.toString(), end.toString(), true);
        return getCacheKeyIntervalValues(start, fromInclusive, end, toInclusive);
    }

    public Set<E> getCacheKeyLesserThanValues(K lesserThanThis, boolean inclusive) {
        return Sets.newHashSet(treeMap().headMap(lesserThanThis, inclusive).values());
    }

    public Set<E> getCacheKeyGreaterThanValues(K greaterThanThis, boolean inclusive) {
        return Sets.newHashSet(treeMap().tailMap(greaterThanThis, inclusive).values());
    }

    public Set<E> getCacheKeyIntervalValues(K start, boolean fromInclusive, K end, boolean toInclusive) {
        return Sets.newHashSet(treeMap().subMap(start, fromInclusive, end, toInclusive).values());
    }

//    public Optional<E> getTopEntity(){
//        //asd
//        return treeMap().f
//    }
}
