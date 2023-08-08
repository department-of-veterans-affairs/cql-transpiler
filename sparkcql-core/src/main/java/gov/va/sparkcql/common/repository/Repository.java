package gov.va.sparkcql.common.repository;

import java.util.List;

public interface Repository<K, T> {

    T findOne(K key);

    List<T> findMany(List<K> keys);

    Boolean exists(K key);
}