package gov.va.sparkcql.retriever;

import java.util.Collection;
import java.util.Map;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import gov.va.sparkcql.model.RetrievalPlan;
import gov.va.sparkcql.model.RetrieveOperation;

public interface BulkRetriever extends Row {

    public Map<RetrieveOperation, Dataset<Row>> retrieve(RetrievalPlan plan);

    public Collection<Object> retrieveContextTraversable(RetrievalPlan plan);

    public Dataset<Row> combine();

    public Boolean isCached();
    
    public String makeCacheToken(RetrieveOperation retrieveOperation);

    // public Object contextBundling();

    // public LocalDateTime sourceRefreshDateTime(DataType...)
}