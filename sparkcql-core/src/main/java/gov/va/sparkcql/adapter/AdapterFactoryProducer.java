package gov.va.sparkcql.adapter;

import java.util.List;
import java.util.ServiceLoader;

import org.apache.spark.sql.SparkSession;

import gov.va.sparkcql.Configuration;

final public class AdapterFactoryProducer {

    private AdapterFactoryProducer() {}

    public static <A extends Adapter, F extends AdapterFactory<A>> List<A> produceAdapters(Class<F> adapterFactoryClass, Configuration configuration, SparkSession spark) {
        var factories = ServiceLoader.load(adapterFactoryClass);
        var adapters = factories.stream().map(f -> f.get().create(configuration, spark)).toList();
        return adapters;
    }    
}
