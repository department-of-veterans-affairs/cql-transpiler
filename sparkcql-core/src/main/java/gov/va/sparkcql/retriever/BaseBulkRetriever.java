package gov.va.sparkcql.retriever;

import java.util.Collection;
import java.util.Map;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import gov.va.sparkcql.model.RetrievalPlan;
import gov.va.sparkcql.model.RetrieveOperation;

public class BaseBulkRetriever implements BulkRetriever {

    public BaseBulkRetriever(Object modelResolver, Object cacheStore, Object sourceResolver) {
    }

    @Override
    public Map<RetrieveOperation, Dataset<Row>> retrieve(RetrievalPlan plan) {
        // Identify uncached retrievals.

        // For uncached retrievals, retrieve and cache them now.
            
            // Colocate by Patient ID using bucketing to eliminate shuffling during bundling.

        // Bundle all data requirements. Should not incur any shuffling operations as the data should be colocated.

        throw new UnsupportedOperationException("Unimplemented method 'retrieveBundle'");
    }

    @Override
    public Boolean isCached() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isCached'");
    }

    @Override
    public String makeCacheToken(RetrieveOperation retrieveOperation) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'makeCacheToken'");
    }

    @Override
    public Collection<Object> retrieveContextTraversable(RetrievalPlan plan) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retrieveContextTraversable'");
    }

    @Override
    public Row copy() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'copy'");
    }

    @Override
    public Object get(int i) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public int length() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'length'");
    }

    @Override
    public Dataset<Row> combine() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'combine'");
    }
}