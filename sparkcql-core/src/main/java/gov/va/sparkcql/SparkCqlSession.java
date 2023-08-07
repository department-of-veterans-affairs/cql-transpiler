package gov.va.sparkcql;

import java.util.List;
import java.util.Map;

import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.VersionedIdentifier;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import gov.va.sparkcql.common.configuration.ComponentFactory;
import gov.va.sparkcql.common.configuration.ConfigKey;
import gov.va.sparkcql.common.configuration.Configuration;
import gov.va.sparkcql.compiler.Compiler;
import gov.va.sparkcql.repository.CqlSourceRepository;
import gov.va.sparkcql.retriever.BulkRetriever;

public class SparkCqlSession {

    private Compiler compiler;

    public SparkCqlSession(Compiler compiler, BulkRetriever retriever) {
    }

    public Map<String, Dataset<Row>> cql(String libraryName, String version, Map<String, Object> parameters) {
        var compilation = compiler.compile(List.of(new VersionedIdentifier().withId(libraryName).withVersion(version)));
        return execute(compilation, parameters);
    }

    public Map<String, Dataset<Row>> cql(String libraryName, String version) {
        return cql(libraryName, version, Map.of());
    }

    public Map<String, Dataset<Row>> cql(String cqlSource, Map<String, Object> parameters) {
        var compilation = compiler.compile(cqlSource);
        return execute(compilation, parameters);
    }

    public Map<String, Dataset<Row>> cql(String cqlSource) {
        return cql(cqlSource, Map.of());
    }
    
    private Map<String, Dataset<Row>> execute(List<Library> libraries, Map<String, Object> parameters) {
        // var translator = new Translator(spark, modelAdapters, libraryAdapters, dataAdapters);
        //translator.translate(libraries);

        // Generate retrieval plan from compiled libraries

        // Map retrieval operations to their data

        // Combine all operations into context aligned iterator (aka bundle)

        // Range over context (patient, practitioner, etc) evaluating each one.
        // iterator.map(...)

        // Output results to client

        return null;
    }

    public static Builder build() {
        return new Builder();
    }

    static public final class Builder {

        private Builder() {
        }

        Configuration cfg = new Configuration();

        public Builder withConfig(String key, String value) {
            cfg.write(key, value);
            return this;
        }
        
        public Builder withConfig(ConfigKey key, String value) {
            cfg.write(key, value);
            return this;
        }

        public SparkCqlSession create() {
            CqlSourceRepository cqlSourceRepository = 
                ComponentFactory.createFromConfiguration(ConfigKey.SPARKCQL_CQLSOURCEREPOSITORY_CLASS, cfg);
            BulkRetriever bulkRetriever = 
                ComponentFactory.createFromConfiguration(ConfigKey.SPARKCQL_BULKRETRIEVER_CLASS, cfg);
            var compiler = new Compiler(cqlSourceRepository);
            
            return new SparkCqlSession(compiler, bulkRetriever);
        }
    }        
}