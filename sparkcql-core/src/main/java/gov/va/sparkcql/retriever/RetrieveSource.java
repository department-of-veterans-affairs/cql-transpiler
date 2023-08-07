package gov.va.sparkcql.retriever;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import gov.va.sparkcql.model.RetrieveOperation;

public interface RetrieveSource {
    
    public Dataset<Row> retrieve(RetrieveOperation retrieveOperation);
}