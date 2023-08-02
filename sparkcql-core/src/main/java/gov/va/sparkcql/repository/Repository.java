package gov.va.sparkcql.repository;

import java.util.List;

import org.apache.spark.sql.Dataset;

public interface Repository<K, T> {

    T findOne(K key);

    List<T> findMany(List<K> keys);

    Dataset<T> query();

    Dataset<T> query(Object filters);

    Boolean exists(K key);

    default void prefetch(List<K> keys) {
        // Default implementation ignores prefetch
    }
}