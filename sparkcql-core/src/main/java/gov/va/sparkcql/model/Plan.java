package gov.va.sparkcql.model;

import java.util.List;

public class Plan {
    
    private List<RetrievalOperation> retrievalOperations;

    public List<RetrievalOperation> getRetrievalOperations() {
        return retrievalOperations;
    }

    public void setRetrievalOperations(List<RetrievalOperation> retrievalOperations) {
        this.retrievalOperations = retrievalOperations;
    }

    public Plan withRetrievalOperations(List<RetrievalOperation> retrievalOperations) {
        this.retrievalOperations = retrievalOperations;
        return this;
    }

    public String getContextSpecifier() {
        return "Patient";       // TODO
    }
}