package gov.va.sparkcql.runtime;

import gov.va.sparkcql.configuration.Configuration;
import org.apache.spark.sql.SparkSession;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class LocalSparkFactory implements SparkFactory {

    @Override
    public SparkSession create(Configuration configuration) {
        // Overriding the root logger is seems to be the only way to prevent Spark INFO
        // logging. A bad practice for production but for local development it's fine.
        var rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(Level.FATAL);

        // Set the preferred logging.
        var sparkLoggingLevel = Level.ERROR;
        Logger.getLogger("org.apache.spark").setLevel(sparkLoggingLevel);
        Logger.getLogger("org.spark-project").setLevel(sparkLoggingLevel);

        // Build a spark session.
        var spark = SparkSession.builder()
                .master("local[2]")
                .getOrCreate();

        // Set the preferred log level again but at the newly created context level.
        spark.sparkContext().setLogLevel(sparkLoggingLevel.toString());

        return spark;
    }
}