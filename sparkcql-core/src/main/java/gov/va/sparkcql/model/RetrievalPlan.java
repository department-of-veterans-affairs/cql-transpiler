package gov.va.sparkcql.model;

import gov.va.sparkcql.retriever.ContextDataIterator;

public interface RetrievalPlan {
    public ContextDataIterator retrieve(RetrievalPlan plan);
}
