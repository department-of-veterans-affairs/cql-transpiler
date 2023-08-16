package gov.va.sparkcql.repository.clinical;

import java.lang.reflect.ParameterizedType;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;

import com.google.inject.Inject;

import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.log.Log;

public abstract class SparkClinicalRepository<T> implements ClinicalRepository<T> {

    protected SparkSession spark;

    @Inject
    public SparkClinicalRepository(SparkFactory sparkFactory) {
        this.spark = sparkFactory.create();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<T> getEntityClass() {
        var clazz = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return clazz;
    }

    protected abstract StructType getCanonicalSchema();

    protected void validateSchema(StructType schema) {
        if (!schema.toDDL().equals(getCanonicalSchema().toDDL())) {
            Log.warn(schema.toDDL());
            Log.warn(getCanonicalSchema().toDDL());
            throw new RuntimeException("Invalid schema for ClinicalDataRepository<" + getEntityClass().getSimpleName() + ">.");
        }
    }

    @Override
    public Dataset<Row> acquire() {
        var ds = acquire();
        validateSchema(ds.schema());
        return ds;
    }

    protected String resolveTable() {
        return getEntityClass().getSimpleName();
    }
}