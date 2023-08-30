package gov.va.sparkcql.fixture.mock;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.pipeline.model.ModelAdapter;
import gov.va.sparkcql.pipeline.model.ModelAdapterFactory;

public class MockModelAdapterFactory extends ModelAdapterFactory {

    public MockModelAdapterFactory(Configuration configuration) {
        super(configuration);
    }

    @Override
    public ModelAdapter create() {
        return new MockModelAdapter();
    }
}