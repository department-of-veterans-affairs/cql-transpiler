package gov.va.sparkcql.retriever;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import gov.va.sparkcql.model.RetrieveOperation;

public class BaseSparkRetrieveSource implements RetrieveSource {

    @Override
    public Dataset<Row> retrieve(RetrieveOperation retrieveOperation) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retrieve'");
    }
    
}