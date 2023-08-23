package gov.va.sparkcql.repository.clinical;

import java.util.List;
import java.util.stream.Stream;

import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;

import gov.va.sparkcql.io.Resources;
import gov.va.sparkcql.log.Log;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategy;

public final class SyntheticMount {

    private SyntheticMount() {
    }

    public static void mountData(SparkSession spark, FhirSparkRepository<?> repo, TableResolutionStrategy tableResolutionStrategy) {
        try {
            var json = Resources.read("tables/" + repo.getEntityDataType().getName() + ".json");
            var jsonDs = spark.createDataset(List.of(json), Encoders.STRING());
            var rawDs = spark.read().schema(repo.getCanonicalSchema()).json(jsonDs);
            var tableName = tableResolutionStrategy.resolveTableBinding(repo.getEntityDataType());
            var existing = Stream.of(spark.sqlContext().tableNames()).filter(t -> t.toLowerCase().equals(tableName.toLowerCase()));
            if (existing.count() == 0) {
                rawDs.createTempView(tableName);
            } else {
                Log.warn("Temporary table " + tableName + " already exists. Ignoring synthetic mount.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }    
}