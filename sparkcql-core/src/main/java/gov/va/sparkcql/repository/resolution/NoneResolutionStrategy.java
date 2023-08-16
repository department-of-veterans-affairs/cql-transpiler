package gov.va.sparkcql.repository.resolution;

import gov.va.sparkcql.types.DataType;

public class NoneResolutionStrategy implements TableResolutionStrategy {

    @Override
    public String resolveTableBinding(DataType dataType) {
        return null;
    }
}
