package gov.va.sparkcql.fhir;

import org.apache.spark.sql.SparkSession;

import gov.va.sparkcql.Configuration;
import gov.va.sparkcql.adapter.model.ModelAdapter;
import gov.va.sparkcql.adapter.model.ModelAdapterFactory;

public class FhirModelAdapterFactory implements ModelAdapterFactory {

    @Override
    public ModelAdapter create(Configuration configuration, SparkSession spark) {
        return new FhirModelAdapter();
    }
}