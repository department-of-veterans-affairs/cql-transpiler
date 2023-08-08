package gov.va.sparkcql.retriever;

import java.util.Collection;
import java.util.Map;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import gov.va.sparkcql.model.RetrievalPlan;
import gov.va.sparkcql.model.RetrievalOperation;

public interface BulkRetriever {

    public Map<RetrievalOperation, Dataset<Row>> retrieve(RetrievalPlan plan);

    public Collection<Object> retrieveContextTraversable(RetrievalPlan plan);

    public Dataset<Row> combine();

    public Boolean isCached();
    
    public String makeCacheToken(RetrievalOperation retrieveOperation);

    // public Object contextBundling();

    // public LocalDateTime sourceRefreshDateTime(DataType...)
}