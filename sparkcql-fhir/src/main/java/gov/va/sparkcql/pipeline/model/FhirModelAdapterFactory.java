package gov.va.sparkcql.pipeline.model;

import gov.va.sparkcql.configuration.Configuration;

public class FhirModelAdapterFactory implements ModelAdapterFactory {

    @Override
    public ModelAdapter create(Configuration configuration) {
        return new FhirModelAdapter();
    }
}