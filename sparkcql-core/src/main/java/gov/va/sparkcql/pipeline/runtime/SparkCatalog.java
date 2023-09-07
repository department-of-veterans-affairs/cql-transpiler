package gov.va.sparkcql.pipeline.runtime;

import gov.va.sparkcql.log.Log;
import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;

import java.util.Arrays;

public final class SparkCatalog {

    private SparkCatalog() {
    }

    public static boolean tableExists(SparkSession spark, String tableName) {
        var tables = spark.sqlContext().tableNames();
        return Arrays.asList(tables).contains(tableName);
    }

    public static void safeCreateView(Dataset<?> ds, String tableName) {
        if (!SparkCatalog.tableExists(ds.sparkSession(), tableName)) {
            try {
                ds.createTempView(tableName);
            } catch (AnalysisException e) {
                throw new RuntimeException(e);
            }
        } else {
            Log.warn("Table " + tableName + " already exists. Skipping creation.");
        }
    }
}