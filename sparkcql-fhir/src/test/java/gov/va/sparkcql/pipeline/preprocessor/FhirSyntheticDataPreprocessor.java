package gov.va.sparkcql.pipeline.preprocessor;

import gov.va.sparkcql.runtime.SparkFactory;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategy;
import gov.va.sparkcql.types.DataType;

public class FhirSyntheticDataPreprocessor extends AbstractBoxedDataPreprocessor {

    public FhirSyntheticDataPreprocessor(SparkFactory sparkFactory, TableResolutionStrategy tableResolutionStrategy) {
        super(sparkFactory, tableResolutionStrategy);
    }

    @Override
    public void apply() {
        fromResourceJson("table/patient.json", new DataType("http://hl7.org/fhir", "Patient"));
        fromResourceJson("table/encounter.json", new DataType("http://hl7.org/fhir", "Encounter"));
        fromResourceJson("table/condition.json", new DataType("http://hl7.org/fhir", "Condition"));
    }
}