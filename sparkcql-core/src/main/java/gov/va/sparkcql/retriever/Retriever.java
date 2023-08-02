package gov.va.sparkcql.retriever;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import gov.va.sparkcql.planner.RetrievalPlan;
import gov.va.sparkcql.planner.RetrieveOperation;

public class Retriever {

    public Dataset<Row> retrieveBundle(RetrievalPlan plan) {

        // Identify uncached retrievals.

        // For uncached retrievals, retrieve and cache them now.
            
            // Colocate by Patient ID using bucketing to eliminate shuffling during bundling.

        // Bundle all data requirements. Should not incur any shuffling operations as the data should be colocated.

        return null;
    }

    public Boolean isCached() {
        return false;
    }

    public String makeCacheToken(RetrieveOperation retrieveOperation) {
        return null;
    }    
}