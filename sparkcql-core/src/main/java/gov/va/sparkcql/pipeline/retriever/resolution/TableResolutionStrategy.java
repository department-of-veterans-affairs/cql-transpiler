package gov.va.sparkcql.pipeline.retriever.resolution;

import gov.va.sparkcql.types.DataType;

public interface TableResolutionStrategy {

    public String resolveTableBinding(DataType dataType);
}