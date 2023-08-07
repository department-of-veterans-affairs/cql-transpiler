package gov.va.sparkcql.repository;

import java.util.List;

public interface Repository<K, T> {

    T findOne(K key);

    List<T> findMany(List<K> keys);

    Boolean exists(K key);
}