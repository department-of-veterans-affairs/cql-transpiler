package gov.va.sparkcql.pipeline.preprocessor;

import gov.va.sparkcql.pipeline.model.ModelAdapterCollection;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategy;
import gov.va.sparkcql.runtime.SparkFactory;
import gov.va.sparkcql.types.DataType;

public class FhirSyntheticDataPreprocessor extends AbstractIndexedDataPreprocessor {

    public FhirSyntheticDataPreprocessor(SparkFactory sparkFactory, TableResolutionStrategy tableResolutionStrategy, ModelAdapterCollection modelAdapterCollection) {
        super(sparkFactory, tableResolutionStrategy, modelAdapterCollection);
    }

    @Override
    public void apply() {
        // TODO: MAKE LOCATION CONFIGURABLE
        fromResourceJson("fhir/table/patient.json", new DataType("http://hl7.org/fhir", "Patient"));
        fromResourceJson("fhir/table/encounter.json", new DataType("http://hl7.org/fhir", "Encounter"));
        fromResourceJson("fhir/table/condition.json", new DataType("http://hl7.org/fhir", "Condition"));
        fromResourceJson("fhir/table/medication-administration.json", new DataType("http://hl7.org/fhir", "MedicationAdministration"));
    }
}