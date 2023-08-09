package gov.va.sparkcql.retriever;

import java.util.List;

import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.types.StructType;

import gov.va.sparkcql.common.io.Resources;

public class SampleClinicalDataRepository extends SparkClinicalDataRepository {

    @Override
    protected String resolveTable(Class<?> clazz) {
        var json = List.of(Resources.read("data/sample-clinical-table.json"));
        var jsonDs = spark.createDataset(json, Encoders.STRING());
        var rowsDs = spark.read().json(jsonDs);
        
        var ddl = rowsDs.schema().toDDL();
        ddl = ddl.replace("primaryStartDate STRING", "primaryStartDate TIMESTAMP");
        ddl = ddl.replace("primaryEndDate STRING", "primaryEndDate TIMESTAMP");
        var schema = StructType.fromDDL(ddl);
        
        var ds = spark.read().schema(schema).json(rowsDs.toJSON());
        var table = super.resolveTable(clazz);
        try {
            ds.createTempView(table);
        } catch (AnalysisException e) {
            e.printStackTrace();
        }
        return table;
    }
}