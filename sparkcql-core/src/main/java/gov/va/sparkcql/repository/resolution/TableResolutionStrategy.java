package gov.va.sparkcql.repository.resolution;

import gov.va.sparkcql.types.DataType;

public interface TableResolutionStrategy {

    public String resolveTableBinding(DataType dataType);
}