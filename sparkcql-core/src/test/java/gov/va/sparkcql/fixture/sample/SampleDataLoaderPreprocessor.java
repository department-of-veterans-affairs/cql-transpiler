package gov.va.sparkcql.fixture.sample;

import java.util.List;

import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;

import gov.va.sparkcql.io.Resources;
import gov.va.sparkcql.pipeline.Pipeline;
import gov.va.sparkcql.pipeline.preprocessor.Preprocessor;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategy;
import gov.va.sparkcql.types.DataType;

public class SampleDataLoaderPreprocessor implements Preprocessor {

    private TableResolutionStrategy tableResolutionStrategy;

    public SampleDataLoaderPreprocessor(TableResolutionStrategy tableResolutionStrategy) {
        this.tableResolutionStrategy = tableResolutionStrategy;
    }

    @Override
    public void apply(Pipeline pipeline) {
        loadData(
            pipeline.getSpark(),
            "sample/sample-data-patient.json",
            new DataType("http://va.gov/sparkcql/sample", "SamplePatient"));
        loadData(
            pipeline.getSpark(),
            "sample/sample-data-entity.json",
            new DataType("http://va.gov/sparkcql/sample", "SampleEntity"));
    }
    
    private void loadData(SparkSession spark, String jsonPath, DataType dataType) {
        try {
            // Load json from resources using schema inference. The json format is assumed to 
            // conform to the box encoded schematic.
            var json = List.of(Resources.read(jsonPath));
            var jsonDs = spark.createDataset(json, Encoders.STRING());
            var rawDs = spark.read().json(jsonDs);

            // However, some fields won't get inferred correctly so tweak those.
            var ddl = rawDs.schema().toDDL();
            ddl = ddl.replace("primaryStartDate STRING", "primaryStartDate TIMESTAMP");
            ddl = ddl.replace("primaryEndDate STRING", "primaryEndDate TIMESTAMP");
            var schema = StructType.fromDDL(ddl);

            // Mount the data using the 
            var mountTableName = tableResolutionStrategy.resolveTableBinding(dataType);
            var ds = spark.read().schema(schema).json(rawDs.toJSON());        
            ds.createGlobalTempView(mountTableName);
        } catch (AnalysisException e) {
            throw new RuntimeException(e);
        }
    }
}