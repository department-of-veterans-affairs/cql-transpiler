package gov.va.sparkcql.pipeline.preprocessor;

import com.google.inject.Inject;
import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategy;
import gov.va.sparkcql.types.DataType;

public class SyntheticDataPreprocessor extends AbstractBoxedDataPreprocessor {

    @Inject
    public SyntheticDataPreprocessor(SparkFactory sparkFactory, TableResolutionStrategy tableResolutionStrategy) {
        super(sparkFactory, tableResolutionStrategy);
    }

    @Override
    public void apply() {
        fromResourceJson("tables/patient.json", new DataType("http://hl7.org/fhir", "Patient"));
        fromResourceJson("tables/encounter.json", new DataType("http://hl7.org/fhir", "Encounter"));
        fromResourceJson("tables/condition.json", new DataType("http://hl7.org/fhir", "Condition"));
    }
}