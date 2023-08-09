package gov.va.sparkcql.repository;

import org.apache.spark.sql.Dataset;

public interface QueryableRepository<T> {
    
    Dataset<T> queryable();
}