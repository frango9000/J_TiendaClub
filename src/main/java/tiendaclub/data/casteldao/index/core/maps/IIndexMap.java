package tiendaclub.data.casteldao.index.core.maps;

import com.google.common.collect.SetMultimap;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface IIndexMap<K, V> extends SetMultimap<K, V> {

    boolean remove(@Nullable K key);

    V getValue(@Nullable K key);


}
