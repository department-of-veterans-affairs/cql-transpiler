package gov.va.sparkcql.repository;

import java.util.List;

import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;

import static org.apache.spark.sql.functions.*;
import org.apache.spark.sql.SparkSession;
import org.hl7.fhir.r4.model.Resource;

import gov.va.sparkcql.common.configuration.Configuration;
import gov.va.sparkcql.common.configuration.ConfigKey;
import gov.va.sparkcql.common.factory.SparkFactory;
import gov.va.sparkcql.model.ClinicalTable;

public abstract class FhirSparkRepository<T extends Resource> implements FhirRepository<T> {

    protected SparkSession spark;
    protected String tableName;

    public FhirSparkRepository() {
        this(Configuration.getGlobalConfig());
    }

    public FhirSparkRepository(Configuration cfg) {
        this(cfg.read(ConfigKey.SPARKCQL_DEFAULT_TABLEBINDING));
    }

    public FhirSparkRepository(String tablebinding) {
        this.spark = SparkFactory.create();
        
        this.tableName = tablebinding;
        tableName = tableName.replace("${type}", getEntityClass().getSimpleName()).toLowerCase();
        tableName = tableName.replace("${TYPE}", getEntityClass().getSimpleName()).toUpperCase();
        tableName = tableName.replace("${Type}", getEntityClass().getSimpleName());
    }

    protected abstract Class<T> getEntityClass();

    @Override
    public Encoder<T> getEncoder() {
        return Encoders.bean(getEntityClass());
    }

    @Override
    public ClinicalTable<T> findOne(String key) {
        var ds = spark.table(this.tableName).as(getEncoder());
        var r = ds.filter((FilterFunction<T>)f -> f.getId() == key).first();
        return null;
    }

    @Override
    public List<ClinicalTable<T>> findMany(List<String> keys) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findMany'");
    }

    @Override
    public Boolean exists(String key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exists'");
    }
}