package gov.va.sparkcql;

import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import javax.xml.namespace.QName;

import org.apache.spark.sql.SparkSession;
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.VersionedIdentifier;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import gov.va.sparkcql.adapter.Adapter;
import gov.va.sparkcql.adapter.AdapterFactory;
import gov.va.sparkcql.adapter.AdapterFactoryProducer;
import gov.va.sparkcql.adapter.data.DataAdapter;
import gov.va.sparkcql.adapter.data.DataAdapterAggregator;
import gov.va.sparkcql.adapter.data.DataAdapterFactory;
import gov.va.sparkcql.adapter.library.LibraryAdapter;
import gov.va.sparkcql.adapter.library.LibraryAdapterFactory;
import gov.va.sparkcql.adapter.model.ModelAdapter;
import gov.va.sparkcql.adapter.model.ModelAdapterFactory;
import gov.va.sparkcql.compiler.Compiler;

public class Session {

    private SparkSession spark;
    private Configuration configuration = new Configuration();
    private Compiler compiler;
    private List<ModelAdapter> modelAdapters;
    private List<LibraryAdapter> libraryAdapters;
    private List<DataAdapter> dataAdapters;

    public Session(SparkSession spark, Configuration configuration) {
        this.spark = spark;
        this.configuration = configuration;
        this.modelAdapters = AdapterFactoryProducer.produceAdapters(ModelAdapterFactory.class, configuration, spark);
        this.libraryAdapters = AdapterFactoryProducer.produceAdapters(LibraryAdapterFactory.class, configuration, spark);
        this.dataAdapters = AdapterFactoryProducer.produceAdapters(DataAdapterFactory.class, configuration, spark);
        this.compiler = new Compiler(this.libraryAdapters);
    }

    protected <A extends Adapter, F extends AdapterFactory<A>> List<A> produceAdapters(Class<F> adapterFactoryClass) {
        var factories = ServiceLoader.load(adapterFactoryClass);
        var adapters = factories.stream().map(f -> f.get().create(configuration, spark)).toList();
        return adapters;
    }

    public Map<String, Dataset<Row>> cql(String libraryName, String version, Map<String, Object> parameters) {
        var compilation = compiler.compile(List.of(new VersionedIdentifier().withId(libraryName).withVersion(version)));
        return evaluate(compilation, parameters);
    }

    public Map<String, Dataset<Row>> cql(String libraryName, String version) {
        return cql(libraryName, version, Map.of());
    }

    public Map<String, Dataset<Row>> cql(String cqlSource, Map<String, Object> parameters) {
        var compilation = compiler.compile(cqlSource);
        return evaluate(compilation, parameters);
    }

    public Map<String, Dataset<Row>> cql(String cqlSource) {
        return cql(cqlSource, Map.of());
    }

    public Dataset<Row> retrieve(QName dataType) {
        var dataAggregate = new DataAdapterAggregator(dataAdapters);
        return dataAggregate.acquireData(dataType);
    }
    
    // public Dataset<Row> evaluateExpression(String cqlSource) {
    //     return evaluateExpression(cqlSource, null);
    // }

    private Map<String, Dataset<Row>> evaluate(List<Library> libraries, Map<String, Object> parameters) {
        // var translator = new Translator(spark, modelAdapters, libraryAdapters, dataAdapters);
        //translator.translate(libraries);
        return null;
    }
}