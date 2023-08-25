package gov.va.sparkcql.fixture;

import com.google.inject.Inject;
import gov.va.sparkcql.pipeline.preprocessor.AbstractBoxedDataPreprocessor;

import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategy;
import gov.va.sparkcql.types.DataType;

public class SampleDataPreprocessor extends AbstractBoxedDataPreprocessor {

    @Inject
    public SampleDataPreprocessor(SparkFactory sparkFactory, TableResolutionStrategy tableResolutionStrategy) {
        super(sparkFactory, tableResolutionStrategy);
    }

    @Override
    public void apply() {
        fromResourceJson("sample/sample-data-patient.json", new DataType("http://va.gov/sparkcql/sample", "SamplePatient"));
        fromResourceJson("sample/sample-data-entity.json", new DataType("http://va.gov/sparkcql/sample", "SampleEntity"));
    }
}