package gov.va.sparkcql.mock;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.pipeline.model.ModelAdapter;
import gov.va.sparkcql.pipeline.model.ModelAdapterFactory;

public class MockModelAdapterFactory implements ModelAdapterFactory {

    @Override
    public ModelAdapter create(Configuration configuration) {
        return new MockModelAdapter();
    }
}