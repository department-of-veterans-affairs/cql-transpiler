package gov.va.sparkcql.synthetic;

import org.apache.spark.sql.SparkSession;

import gov.va.sparkcql.Configuration;
import gov.va.sparkcql.adapter.data.DataAdapter;
import gov.va.sparkcql.adapter.data.DataAdapterFactory;

public class SyntheaDataAdapterFactory implements DataAdapterFactory {

    protected static final String CONFIG_POPULATION_SIZE = "sparkcql.synthea.size";

    @Override
    public DataAdapter create(Configuration configuration, SparkSession spark) {
        var size = PopulationSize.valueOf(configuration.read(CONFIG_POPULATION_SIZE, "PopulationSizeNone"));
        return new SyntheaSourceAdapter(spark, size);
    }
}