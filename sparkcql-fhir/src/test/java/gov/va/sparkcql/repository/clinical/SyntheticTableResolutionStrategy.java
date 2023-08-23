package gov.va.sparkcql.repository.clinical;

import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategy;
import gov.va.sparkcql.types.DataType;

public class SyntheticTableResolutionStrategy implements TableResolutionStrategy {

    @Override
    public String resolveTableBinding(DataType dataType) {
        return "synthetic_fhir_" + dataType.getName().toLowerCase();
    }
}