package gov.va.sparkcql.repository.clinical;

import java.util.List;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.types.StructType;

import com.google.inject.Inject;

import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.io.Resources;
import gov.va.sparkcql.repository.clinical.SparkClinicalRepository;

public abstract class SampleDataRepository<T> extends SparkClinicalRepository<T> {

    @Inject
    public SampleDataRepository(SparkFactory sparkFactory) {
        super(sparkFactory);
    }

    private Dataset<Row> getRawData() {
        var json = List.of(Resources.read(getJsonDataPath()));
        var jsonDs = spark.createDataset(json, Encoders.STRING());
        return spark.read().json(jsonDs);
    }
    
    @Override
    protected StructType getCanonicalSchema() {
        var rawDs = getRawData();
        var ddl = rawDs.schema().toDDL();
        ddl = ddl.replace("primaryStartDate STRING", "primaryStartDate TIMESTAMP");
        ddl = ddl.replace("primaryEndDate STRING", "primaryEndDate TIMESTAMP");
        return StructType.fromDDL(ddl);
    }

    @Override
    public Dataset<Row> acquire() {
        var rawDs = getRawData();
        var ds = spark.read().schema(getCanonicalSchema()).json(rawDs.toJSON());
        return ds;        
    }

    protected abstract String getJsonDataPath();
}