package gov.va.sparkcql.repository;

import org.apache.spark.sql.Dataset;

import gov.va.sparkcql.common.repository.Repository;

public interface QueryableRepository<K, T> extends Repository<K, T> {

    Dataset<T> query();

    Dataset<T> query(Object filters);
}