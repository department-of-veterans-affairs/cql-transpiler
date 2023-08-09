package gov.va.sparkcql.retriever;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;

import gov.va.sparkcql.common.di.ServiceContext;
import gov.va.sparkcql.common.spark.SparkFactory;

public abstract class SparkClinicalDataRepository implements ClinicalDataRepository {

    protected SparkSession spark;

    public SparkClinicalDataRepository() {
        this.spark = ServiceContext.createOne(SparkFactory.class).create();
    }

    protected void validateSchema(StructType schema) {
        validateColumn(schema, "patientCorrelationId", "string");
        validateColumn(schema, "practictionerCorrelationId", "string");
        validateColumn(schema, "primaryCode", "string");
        validateColumn(schema, "primaryStartDate", "timestamp");
        validateColumn(schema, "primaryEndDate", "timestamp");
        validateColumn(schema, "dataType", "string");
        validateColumn(schema, "data", "struct");
    }

    private void validateColumn(StructType schema, String columnName, String typeName) {
        typeName = typeName.toLowerCase();
        if (schema.getFieldIndex(columnName).isEmpty()) {
            throw new RuntimeException("Invalid Schema: Missing " + columnName + " column.");
        }
        var index = schema.fieldIndex(columnName);
        var field = schema.fields()[index];
        var fieldTypeName = field.dataType().typeName();
        if (!fieldTypeName.equals(typeName)) {
            throw new RuntimeException("Invalid Schema: Expected type " + typeName + " for column " + columnName + " but found " + fieldTypeName);
        }
    }

    @Override
    public Dataset<Row> acquireQueryable(Class<?> clazz) {
        var ds = spark.table(resolveTable(clazz));
        validateSchema(ds.schema());
        return ds;
    }

    protected String resolveTable(Class<?> clazz) {
        return clazz.getSimpleName();
    }
}