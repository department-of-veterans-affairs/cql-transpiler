package gov.va.sparkcql.repository.clinical;

import java.util.List;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.types.StructType;

import com.google.inject.Inject;

import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.io.Resources;
import gov.va.sparkcql.repository.resolution.TableResolutionStrategy;

public abstract class SampleClinicalRepository<T> extends SparkClinicalRepository<T> {

    @Inject
    public SampleClinicalRepository(SparkFactory sparkFactory, TableResolutionStrategy tableResolutionStrategy) {
        super(sparkFactory, tableResolutionStrategy);
    }

    private Dataset<Row> getRawData() {
        var json = List.of(Resources.read(getJsonDataPath()));
        var jsonDs = spark.createDataset(json, Encoders.STRING());
        return spark.read().json(jsonDs);
    }

    @Override
    public Dataset<Row> acquire() {
        var json = List.of(Resources.read(getJsonDataPath()));
        var jsonDs = spark.createDataset(json, Encoders.STRING());
        var rawDs = spark.read().json(jsonDs);
        var ds = spark.read().schema(getCanonicalSchema()).json(rawDs.toJSON());
        SparkClinicalSchemaHelper.validateSchema(ds.schema(), getCanonicalSchema(), getEntityDataType());
        return ds;
    }

    @Override
    protected StructType getCanonicalSchema() {
        var rawDs = getRawData();
        var ddl = rawDs.schema().toDDL();
        ddl = ddl.replace("primaryStartDate STRING", "primaryStartDate TIMESTAMP");
        ddl = ddl.replace("primaryEndDate STRING", "primaryEndDate TIMESTAMP");
        return StructType.fromDDL(ddl);
    }

    protected abstract String getJsonDataPath();
}