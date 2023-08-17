package gov.va.sparkcql.repository;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.lit;
import static org.apache.spark.sql.functions.explode;
import static org.apache.spark.sql.functions.struct;
import static org.apache.spark.sql.functions.to_json;

import java.util.List;
import java.util.Map;

import static org.apache.spark.sql.functions.from_json;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.io.Files;
import gov.va.sparkcql.io.Resources;
import gov.va.sparkcql.repository.clinical.FhirSparkRepository;

public class SyntheaFhirSparkRepository<T> extends FhirSparkRepository<T> {

    protected static final String DEFAULT_RESOURCE_COLUMN = "data";

    protected SyntheticPopulationSize size;
    
    public SyntheaFhirSparkRepository(SparkFactory sparkFactory, SyntheaTableResolutionStrategy resolutionStrategy, SyntheticPopulationSize size) {
        super(sparkFactory, resolutionStrategy);
        this.size = size;

        try {
            // Returning synthetic data must always be explicit. This is a failsafe
            // for production. The default should be disabled.
            if (this.size != SyntheticPopulationSize.PopulationSizeNone) {
                var options = Map.of("mode", "FAILFAST", "multiLine", "true");
                var dataType = getEntityDataType().getName();

                // Load Synthea bundle data into a dataset. Will contain all resource types
                // at this point which will be filtered further down.
                var bundles = new SyntheaDataLoader().loadBundles(size);
                var dsTextBundles = spark.createDataset(bundles, Encoders.STRING());
                var bundleDs = spark.read().options(options).json(dsTextBundles);
                
                var text = String.join(",", bundleDs.toJSON().collectAsList());
                Files.writeText(text, "theoutput.json");
                
                // Extract each individual resource out of the bundles so there's one row
                // per resource. Restructure to replace the bundle model with the Table Container
                // model. Package up the resource as JSON (so the inferred schema can be replaced
                // with the canonical form).
                
                var tableDs = bundleDs
                    .select(explode(col("entry")).as("entry"))
                    .where(col("entry.resource.resourceType").equalTo(lit(dataType)))
                    .select(
                        col("entry.resource.subject.reference").as("patientCorrelationId"),
                        lit(null).as("practictionerCorrelationId"),
                        lit(null).as("primaryCode"),
                        lit(null).as("primaryStartDate"),
                        lit(null).as("primaryEndDate"),
                        col("entry.resource.resourceType").as("dataType"),
                        struct(col("entry.resource.*")).as(DEFAULT_RESOURCE_COLUMN)
                        )
                    .select(to_json(struct(col("*"))).alias("json"));

                // Homogenize the rows to the same dataType using a filter.                 
                var canonicalSchema = this.getCanonicalSchema();
                var schema = new StructType(new StructField[] {
                    new StructField("json", canonicalSchema, false, Metadata.empty())
                });
                
                var ds = tableDs
                    .select(from_json(col("json"), canonicalSchema).as("instance"))
                    .select(col("instance.*"))
                    .where(col("dataType").equalTo(dataType));
                                    
                var tableName = new SyntheaTableResolutionStrategy().resolveTableBinding(getEntityDataType());
                ds.createTempView(tableName);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}