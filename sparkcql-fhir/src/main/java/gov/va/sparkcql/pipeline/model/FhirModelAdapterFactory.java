package gov.va.sparkcql.pipeline.model;

import gov.va.sparkcql.configuration.Configuration;

public class FhirModelAdapterFactory extends ModelAdapterFactory {

    public FhirModelAdapterFactory(Configuration configuration) {
        super(configuration);
    }

    @Override
    public ModelAdapter create() {
        return new FhirModelAdapter();
    }
}