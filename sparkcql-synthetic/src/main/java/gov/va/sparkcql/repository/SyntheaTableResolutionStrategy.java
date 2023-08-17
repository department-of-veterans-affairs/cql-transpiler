package gov.va.sparkcql.repository;

import gov.va.sparkcql.repository.resolution.TableResolutionStrategy;
import gov.va.sparkcql.types.DataType;

public class SyntheaTableResolutionStrategy implements TableResolutionStrategy {

    @Override
    public String resolveTableBinding(DataType dataType) {
        return "synthea_fhir_" + dataType.getName();
    }
}