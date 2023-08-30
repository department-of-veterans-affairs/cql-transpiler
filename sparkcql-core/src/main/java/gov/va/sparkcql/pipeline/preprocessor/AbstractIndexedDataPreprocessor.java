package gov.va.sparkcql.pipeline.preprocessor;

import gov.va.sparkcql.runtime.SparkCatalog;
import gov.va.sparkcql.runtime.SparkFactory;
import gov.va.sparkcql.io.Resources;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategy;
import gov.va.sparkcql.types.DataType;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;

import java.util.List;

public abstract class AbstractIndexedDataPreprocessor implements Preprocessor {

    private final SparkSession spark;
    private final TableResolutionStrategy tableResolutionStrategy;

    public AbstractIndexedDataPreprocessor(SparkFactory sparkFactory, TableResolutionStrategy tableResolutionStrategy) {
        this.spark = sparkFactory.create();
        this.tableResolutionStrategy = tableResolutionStrategy;
    }

    protected void fromResourceJson(String resourceJsonPath, DataType dataType) {
        // Load json from resources using schema inference. The json format is assumed to
        // conform to the box encoded schematic.
        var json = List.of(Resources.read(resourceJsonPath));
        var jsonDs = spark.createDataset(json, Encoders.STRING());
        var rawDs = spark.read().json(jsonDs);

        // However, some fields won't get inferred correctly so tweak those.
        var ddl = rawDs.schema().toDDL();
        ddl = ddl.replace("primaryStartDate STRING", "primaryStartDate TIMESTAMP");
        ddl = ddl.replace("primaryEndDate STRING", "primaryEndDate TIMESTAMP");
        var schema = StructType.fromDDL(ddl);

        // Mount the data
        var mountTableName = tableResolutionStrategy.resolveTableBinding(dataType);
        var ds = spark.read().schema(schema).json(rawDs.toJSON());
        SparkCatalog.safeCreateView(ds, mountTableName);
    }
}