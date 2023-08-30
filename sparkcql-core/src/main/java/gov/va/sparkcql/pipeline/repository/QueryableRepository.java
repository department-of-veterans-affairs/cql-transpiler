package gov.va.sparkcql.pipeline.repository;

import org.apache.spark.sql.Dataset;

public interface QueryableRepository<T> extends Repository {
    
    Dataset<T> acquire();
}