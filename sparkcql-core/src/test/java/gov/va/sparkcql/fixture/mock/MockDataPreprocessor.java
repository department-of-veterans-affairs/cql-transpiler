package gov.va.sparkcql.fixture.mock;

import gov.va.sparkcql.pipeline.model.ModelAdapterCollection;
import gov.va.sparkcql.pipeline.preprocessor.AbstractIndexedDataPreprocessor;

import gov.va.sparkcql.runtime.SparkFactory;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategy;
import gov.va.sparkcql.types.DataType;

public class MockDataPreprocessor extends AbstractIndexedDataPreprocessor {

    public MockDataPreprocessor(SparkFactory sparkFactory, TableResolutionStrategy tableResolutionStrategy, ModelAdapterCollection modelAdapterCollection) {
        super(sparkFactory, tableResolutionStrategy, modelAdapterCollection);
    }

    @Override
    public void apply() {
        fromResourceJson("mock-model/table/mock-data-patient.json", new DataType("http://va.gov/sparkcql/mock", "MockPatient"));
        fromResourceJson("mock-model/table/mock-data-entity.json", new DataType("http://va.gov/sparkcql/mock", "MockEntity"));
    }
}