package gov.va.sparkcql.retriever;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface ClinicalDataRepository {
    
    Dataset<Row> acquireQueryable(Class<?> clazz);
}