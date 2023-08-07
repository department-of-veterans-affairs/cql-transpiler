// package gov.va.sparkcql.repository;

// import org.apache.spark.sql.SparkSession;

// import gov.va.sparkcql.adapter.data.DataAdapter;
// import gov.va.sparkcql.adapter.data.DataAdapterFactory;
// import gov.va.sparkcql.config.Configuration;

// public class SyntheaDataAdapterFactory implements DataAdapterFactory {

//     protected static final String CONFIG_POPULATION_SIZE = "sparkcql.synthea.size";

//     @Override
//     public DataAdapter create(Configuration configuration, SparkSession spark) {
//         var size = SyntheticPopulationSize.valueOf(configuration.read(CONFIG_POPULATION_SIZE, "PopulationSizeNone"));
//         return new SyntheaSourceAdapter(spark, size);
//     }
// }