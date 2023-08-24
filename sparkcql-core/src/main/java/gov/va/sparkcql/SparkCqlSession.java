//package gov.va.sparkcql;
//
//import java.util.List;
//import java.util.Map;
//
//import org.hl7.elm.r1.Library;
//import org.hl7.elm.r1.VersionedIdentifier;
//import org.apache.spark.sql.Dataset;
//import org.apache.spark.sql.Row;
//
//import gov.va.sparkcql.configuration.SystemConfiguration;
//import gov.va.sparkcql.pipeline.compiler.Compiler;
//import gov.va.sparkcql.pipeline.executor.BulkRetriever;
//import gov.va.sparkcql.pipeline.executor.Executor;
//import gov.va.sparkcql.pipeline.planner.Planner;
//
//public class SparkCqlSession {
//
//    private Compiler compiler;
//    private Planner planner;
//    private BulkRetriever retriever;
//    private Executor executor;
//
//    public SparkCqlSession(Compiler compiler, Planner planner, BulkRetriever retriever, Executor executor) {
//        this.compiler = compiler;
//        this.planner = planner;
//        this.retriever = retriever;
//        this.executor = executor;
//    }
//
//    // TODO: Show Canonical Schemas
//
//    public Map<String, Dataset<Row>> eval(String libraryName, String version, Map<String, Object> parameters) {
//        var compilation = compiler.compile(List.of(new VersionedIdentifier().withId(libraryName).withVersion(version)));
//        return eval(compilation, parameters);
//    }
//
//    public Map<String, Dataset<Row>> eval(String libraryName, String version) {
//        return eval(libraryName, version, Map.of());
//    }
//
//    public Map<String, Dataset<Row>> eval(String cqlSource, Map<String, Object> parameters) {
//        var compilation = compiler.compile(cqlSource);
//        return eval(compilation, parameters);
//    }
//
//    public Map<String, Dataset<Row>> eval(String cqlSource) {
//        return eval(cqlSource, Map.of());
//    }
//
//    public Map<String, Dataset<Row>> eval(List<Library> libraries) {
//        return eval(libraries, null);
//    }
//
//    public Map<String, Dataset<Row>> eval(List<Library> libraries, Map<String, Object> parameters) {
//        var plan = this.planner.plan(libraries);
//        var contextData = this.retriever.retrieve(plan, null);
//
//        // var translator = new Translator(spark, modelAdapters, libraryAdapters, dataAdapters);
//        //translator.translate(libraries);
//
//        // Generate retrieval plan from compiled libraries
//
//        // Map retrieval operations to their data
//
//        // Combine all operations into context aligned iterator (aka bundle)
//
//        // Range over context (patient, practitioner, etc) evaluating each one.
//        // iterator.map(...)
//
//        // Output results to client
//
//        return null;
//    }
//
//    public static Builder build() {
//        return new Builder();
//    }
//
//    static public final class Builder {
//
//        private Builder() {
//        }
//
//        SystemConfiguration cfg = new SystemConfiguration();
//
//        public Builder withConfig(String key, String value) {
//            cfg.write(key, value);
//            return this;
//        }
//
//        public SparkCqlSession create() {
//            // CqlSourceRepository cqlSourceRepository = ServiceContext.createOne(CqlSourceRepository.class, cfg);
//            // BulkRetriever bulkRetriever = ServiceContext.createOne(BulkRetriever.class, cfg);
//            //var compiler = new Compiler(cqlSourceRepository);
//
//            // return new SparkCqlSession(null, bulkRetriever);
//            return null;
//        }
//    }
//}