package gov.va.sparkcql.fixture.mock;

import gov.va.sparkcql.pipeline.model.ModelAdapterComposite;
import gov.va.sparkcql.pipeline.preprocessor.AbstractIndexedDataPreprocessor;

import gov.va.sparkcql.runtime.SparkFactory;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategy;
import gov.va.sparkcql.types.DataType;

public class MockDataPreprocessor extends AbstractIndexedDataPreprocessor {

    public MockDataPreprocessor(SparkFactory sparkFactory, TableResolutionStrategy tableResolutionStrategy, ModelAdapterComposite modelAdapterComposite) {
        super(sparkFactory, tableResolutionStrategy, modelAdapterComposite);
    }

    @Override
    public void apply() {
        fromResourceJson("mock-model/table/mock-data-patient.json", new DataType("http://va.gov/sparkcql/mock", "MockPatient"));
        fromResourceJson("mock-model/table/mock-data-entity.json", new DataType("http://va.gov/sparkcql/mock", "MockEntity"));
    }
}