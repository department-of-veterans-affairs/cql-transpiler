package gov.va.sparkcql.adapter;

import org.apache.spark.sql.SparkSession;

import gov.va.sparkcql.Configuration;

public interface AdapterFactory<T extends Adapter> {
    
    public T create(Configuration configuration, SparkSession spark);
}