package tiendaclub.data.framework.index.core;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.function.Function;
import tiendaclub.data.framework.DataSource;
import tiendaclub.data.framework.index.core.maps.TreeIndexMap;
import tiendaclub.data.framework.model.Persistible;

public class SimpleTreeMapIndex<K extends Comparable, V extends Persistible> extends SimpleMapIndex<K, V> {

    public SimpleTreeMapIndex(DataSource<V> dataSource, String indexColumnName, Function<V, K> keyValueFunction) {
        super(dataSource, indexColumnName, keyValueFunction);
        this.index = new TreeIndexMap<K, V>();
    }

    public TreeIndexMap<K, V> treeMap() {
        return (TreeIndexMap<K, V>) index;
    }

    public Set<V> getKeyLesserThanValues(K lesserThanThis, boolean inclusive) {
        dataSource.queryGreaterLesser(indexColumnName, lesserThanThis.toString(), false, inclusive);
        return getCacheKeyLesserThanValues(lesserThanThis, inclusive);
    }

    public Set<V> getKeyGreaterThanValues(K lesserThanThis, boolean inclusive) {
        dataSource.queryGreaterLesser(indexColumnName, lesserThanThis.toString(), true, inclusive);
        return Sets.newHashSet(treeMap().tailMap(lesserThanThis, inclusive).values());
    }

    public Set<V> getKeyIntervalValues(K start, boolean fromInclusive, K end, boolean toInclusive) {
        dataSource.queryBetween(indexColumnName, start.toString(), end.toString(), true);
        return Sets.newHashSet(treeMap().subMap(start, fromInclusive, end, toInclusive).values());
    }

    public Set<V> getCacheKeyLesserThanValues(K lesserThanThis, boolean inclusive) {
        return Sets.newHashSet(treeMap().headMap(lesserThanThis, inclusive).values());
    }

    public Set<V> getCacheKeyGreaterThanValues(K lesserThanThis, boolean inclusive) {
        return Sets.newHashSet(treeMap().tailMap(lesserThanThis, inclusive).values());
    }

    public Set<V> getCacheKeyIntervalValues(K start, boolean fromInclusive, K end, boolean toInclusive) {
        return Sets.newHashSet(treeMap().subMap(start, fromInclusive, end, toInclusive).values());
    }
}
