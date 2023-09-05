package gov.va.sparkcql.fixture.mock;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.pipeline.model.ModelAdapterSet;
import gov.va.sparkcql.pipeline.preprocessor.AbstractIndexedDataPreprocessor;

import gov.va.sparkcql.runtime.SparkFactory;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategy;
import gov.va.sparkcql.types.DataType;

public class MockDataPreprocessor extends AbstractIndexedDataPreprocessor {

    public MockDataPreprocessor(Configuration configuration, SparkFactory sparkFactory, TableResolutionStrategy tableResolutionStrategy, ModelAdapterSet modelAdapterSet) {
        super(configuration, sparkFactory, tableResolutionStrategy, modelAdapterSet);
    }

    @Override
    public void apply() {
        fromResourceJson("mock-model/table/mock-data-patient.json", new DataType("http://va.gov/sparkcql/mock", "MockPatient"));
        fromResourceJson("mock-model/table/mock-data-entity.json", new DataType("http://va.gov/sparkcql/mock", "MockEntity"));
    }
}