package gov.va.sparkcql.pipeline.preprocessor;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.pipeline.model.ModelAdapterSet;
import gov.va.sparkcql.pipeline.runtime.SparkCatalog;
import gov.va.sparkcql.pipeline.runtime.SparkFactory;
import gov.va.sparkcql.io.Resources;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategy;
import gov.va.sparkcql.types.DataType;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;

import java.util.List;

public abstract class AbstractIndexedDataPreprocessor implements Preprocessor {

    private final SparkSession spark;
    private final TableResolutionStrategy tableResolutionStrategy;
    private final ModelAdapterSet modelAdapterSet;

    public AbstractIndexedDataPreprocessor(Configuration configuration, SparkFactory sparkFactory, TableResolutionStrategy tableResolutionStrategy, ModelAdapterSet modelAdapterSet) {
        this.spark = sparkFactory.create(configuration);
        this.tableResolutionStrategy = tableResolutionStrategy;
        this.modelAdapterSet = modelAdapterSet;
    }

    protected void fromResourceJson(String resourceJsonPath, DataType dataType) {
        // The json schema is assumed to conform to the Indexed Table schematic where
        // key attributes from the resource are extracted and promoted as top-level
        // attributes. See indexed-data-table.ddl for more information.
        var dataTypeSchema = modelAdapterSet.forType(dataType).getSchema(dataType);
        var indexedDataSchema = Resources.read("indexed-data-table.ddl");
        var fullSchema = indexedDataSchema.replace("${dataTypeSchema}", dataTypeSchema.toDDL());

        // Using the "indexed" schema with the data type schematic expanded inline,
        // read the JSON. First load the JSON as a list of STRING types then
        // create a Dataset from that applying the full schema. We don't load from
        // disk here b/c it requires a local spark installation & winutils.
        var json = List.of(Resources.read(resourceJsonPath));
        var jsonDs = spark.createDataset(json, Encoders.STRING());
        var multiLineDs = spark.read().json(jsonDs);      // ensures multiple lines are processed correctly
        var ds = spark.read()
                .schema(fullSchema)
                .json(multiLineDs.toJSON());

        // Mount the data
        var mountTableName = tableResolutionStrategy.resolveTableBinding(dataType);
        SparkCatalog.safeCreateView(ds, mountTableName);
    }
}