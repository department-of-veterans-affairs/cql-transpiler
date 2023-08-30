package gov.va.sparkcql.fixture.mock;

import gov.va.sparkcql.pipeline.preprocessor.AbstractBoxedDataPreprocessor;

import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategy;
import gov.va.sparkcql.types.DataType;

public class MockDataPreprocessor extends AbstractBoxedDataPreprocessor {

    public MockDataPreprocessor(SparkFactory sparkFactory, TableResolutionStrategy tableResolutionStrategy) {
        super(sparkFactory, tableResolutionStrategy);
    }

    @Override
    public void apply() {
        fromResourceJson("mock-model/table/mock-data-patient.json", new DataType("http://va.gov/sparkcql/mock", "MockPatient"));
        fromResourceJson("mock-model/table/mock-data-entity.json", new DataType("http://va.gov/sparkcql/mock", "MockEntity"));
    }
}