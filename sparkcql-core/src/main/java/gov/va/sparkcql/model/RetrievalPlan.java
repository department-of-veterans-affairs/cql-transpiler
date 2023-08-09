package gov.va.sparkcql.model;

import java.util.List;

public class RetrievalPlan {
    
    private List<RetrievalOperation> retrievalOperations;

    public List<RetrievalOperation> getRetrievalOperations() {
        return retrievalOperations;
    }

    public void setRetrievalOperations(List<RetrievalOperation> retrievalOperations) {
        this.retrievalOperations = retrievalOperations;
    }

    public RetrievalPlan withRetrievalOperations(List<RetrievalOperation> retrievalOperations) {
        this.retrievalOperations = retrievalOperations;
        return this;
    }
}