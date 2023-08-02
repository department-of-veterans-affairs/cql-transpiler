package gov.va.sparkcql.repository;

public interface MultiTypeRepository<K, T> {
    <R> T findOne(K key, Class<R> classType);
}