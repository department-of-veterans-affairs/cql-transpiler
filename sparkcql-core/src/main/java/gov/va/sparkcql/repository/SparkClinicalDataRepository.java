package gov.va.sparkcql.repository;

import java.lang.reflect.ParameterizedType;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;

import gov.va.sparkcql.common.di.ServiceContext;
import gov.va.sparkcql.common.log.Log;
import gov.va.sparkcql.common.spark.SparkFactory;

public abstract class SparkClinicalDataRepository<T> implements ClinicalDataRepository<T> {

    protected SparkSession spark;

    public SparkClinicalDataRepository() {
        this.spark = ServiceContext.createOne(SparkFactory.class).create();
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
    public Dataset<Row> queryable() {
        var ds = acquire();
        validateSchema(ds.schema());
        return ds;
    }

    protected abstract Dataset<Row> acquire();

    protected String resolveTable() {
        return getEntityClass().getSimpleName();
    }
}