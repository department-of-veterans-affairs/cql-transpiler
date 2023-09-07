package gov.va.sparkcql.pipeline.runtime;

import gov.va.sparkcql.configuration.Configuration;
import org.apache.spark.sql.SparkSession;

public class DefaultSparkFactory implements SparkFactory {

    @Override
    public SparkSession create(Configuration configuration) {
        return SparkSession
                .builder()
                .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
                .config("spark.kryo.registrator", "gov.va.sparkcql.pipeline.model.DefaultKryoRegistrator")
                .getOrCreate();
    }
}