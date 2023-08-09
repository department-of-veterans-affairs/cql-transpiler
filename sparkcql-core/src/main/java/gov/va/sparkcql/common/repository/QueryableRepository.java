package gov.va.sparkcql.common.repository;

import org.apache.spark.sql.Dataset;

public interface QueryableRepository<T> {
    
    Dataset<T> queryable();
}