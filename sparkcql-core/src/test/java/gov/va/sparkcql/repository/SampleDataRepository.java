package gov.va.sparkcql.repository;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.types.StructType;

import com.google.inject.Inject;

import gov.va.sparkcql.common.io.Resources;
import gov.va.sparkcql.common.spark.SparkFactory;

public abstract class SampleDataRepository<T> extends SparkClinicalDataRepository<T> {

    @Inject
    public SampleDataRepository(SparkFactory sparkFactory) {
        super(sparkFactory);
    }

    @Override
    public QName getEntityDataType() {
        return new QName("http://gov.va/sparkcql/sample", getEntityClass().getSimpleName());
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