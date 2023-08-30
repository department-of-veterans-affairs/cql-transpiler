package gov.va.sparkcql.pipeline.retriever.resolution;

import gov.va.sparkcql.types.DataType;

import java.io.Serializable;

public interface TableResolutionStrategy extends Serializable {

    public String resolveTableBinding(DataType dataType);
}