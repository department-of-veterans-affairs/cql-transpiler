package gov.va.sparkcql.common.spark;

import org.apache.spark.sql.SparkSession;

import gov.va.sparkcql.common.configuration.ConfigKey;
import gov.va.sparkcql.common.configuration.Configuration;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class DefaultSparkFactory implements SparkFactory {

    @Override
    public SparkSession create() {
        return create(Configuration.getGlobalConfig());
    }

    @Override
    public SparkSession create(Configuration cfg) {
        var rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(Level.FATAL);
        Logger.getLogger("org.apache.spark").setLevel(Level.WARN);
        Logger.getLogger("org.spark-project").setLevel(Level.WARN);

        var spark = SparkSession.builder()
            .master(cfg.read(ConfigKey.SPARKCQL_SPARK_MASTER.toString(), "local[2]"))
            .config("spark.memory.offHeap.enabled", true)
            .config("spark.memory.offHeap.size", "16g")
            .getOrCreate();
        
        spark.sparkContext().setLogLevel(cfg.read(ConfigKey.SPARKCQL_SPARK_LOGLEVEL, "ERROR"));

        return spark;
    }
}