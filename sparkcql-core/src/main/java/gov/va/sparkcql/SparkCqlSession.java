// package gov.va.sparkcql;

// import java.util.ArrayList;
// import java.util.Iterator;
// import java.util.List;

// import javax.xml.namespace.QName;

// import org.apache.spark.SparkContext;
// import org.apache.spark.api.java.JavaRDD;
// import org.apache.spark.api.java.JavaSparkContext;
// import org.apache.spark.api.java.function.FlatMapFunction2;
// import org.apache.spark.api.java.function.MapFunction;
// import org.apache.spark.api.java.function.MapPartitionsFunction;
// import org.apache.spark.sql.Dataset;
// import org.apache.spark.sql.Encoders;
// import org.apache.spark.sql.Row;
// import org.apache.spark.sql.SparkSession;
// import org.apache.spark.sql.catalyst.encoders.ExpressionEncoder;
// import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema;

// import gov.va.sparkcql.adapter.data.DataAdapter;
// import gov.va.sparkcql.adapter.data.DataAdapterFactory;
// import gov.va.sparkcql.adapter.model.ModelAdapter;
// import gov.va.sparkcql.adapter.model.ModelAdapterFactory;
// import gov.va.sparkcql.logging.Log;

// import org.hl7.elm.r1.AggregateClause;
// import org.hl7.elm.r1.LetClause;

// public class SparkCqlSession {

//     private SparkSession spark;
//     private List<ModelAdapter> models;
//     private List<DataAdapter> sources;

//     private SparkCqlSession(SparkSession spark, List<ModelAdapter> models, List<DataAdapter> sources) {
//         this.spark = spark;
//         this.models = models;
//         this.sources = sources;
//     }
    
//     public <T> Dataset<T> retrieve() {
//         // Class<T> dataType = null;
//         // ModelAdapter model = models.get(0);
//         // SourceAdapter source = sources.get(0);
//         // Log.info(dataType.getSimpleName());
//         throw new RuntimeException();
//     }

//     // public void test() {
//     //     ModelAdapter model = models.get(0);
//     //     SourceAdapter source = sources.get(0);

//     //     var ds1DataType = new QName("http://hl7.org/fhir", "Encounter");
//     //     var ds1Encoder = model.encoderOf(ds1DataType);
//     //     var ds2DataType = new QName("http://hl7.org/fhir", "Condition");
//     //     var ds2Encoder = model.encoderOf(ds2DataType);

//     //     var ds1 = source.acquire(ds1DataType).as(ds1Encoder).alias("DS1");
//     //     //Log.info(ds1);
//     //     var ds2 = source.acquire(ds2DataType).as(ds2Encoder).alias("DS2");
//     //     //Log.info(ds2);
//     //     var dsCombined = ds1.join(ds2, ds1.col("subject.reference").equalTo(ds2.col("subject.reference")));
//     //     var justOne = dsCombined.select("ds2.*");
//     //     var justOneDecoded = justOne.as(ds2Encoder);
//     //     Log.info(justOneDecoded);

//     //     justOne.foreach(item -> {
//     //         GenericRowWithSchema x = (GenericRowWithSchema)item;
//     //         Log.info(x.values()[0].toString());
//     //         Log.info(item.getClass().getSimpleName());
//     //     });

//     // }

//     // public void test2() {
//     //     ModelAdapter model = models.get(0);
//     //     SourceAdapter source = sources.get(0);

//     //     var ds1DataType = new QName("http://hl7.org/fhir", "Encounter");
//     //     var ds1Encoder = model.encoderOf(ds1DataType);
//     //     var ds2DataType = new QName("http://hl7.org/fhir", "Condition");
//     //     var ds2Encoder = model.encoderOf(ds2DataType);

//     //     var ds1 = source.acquire(ds1DataType).as(ds1Encoder);
        
//     //     ExpressionEncoder.tuple(null, null, null, null, null)

//     // }    

//     // public void test1() {
//     //     var encoder = Encoders.bean(AggregateClause.class);
//     //     var encoder2 = Encoders.bean(LetClause.class);
//     //     var ds1 = spark.sql("").as(encoder);
//     //     var ds2 = spark.sql("").as(encoder2); 
        
//     //     //spark.sql("").as(encoder).foreach(f -> f.get);
//     // }

//     // public void test1() {
//     //     var encoder = Encoders.bean(AggregateClause.class);
//     //     var encoder2 = Encoders.bean(LetClause.class);
//     //     var ds1 = spark.sql("").as(encoder);


//     //     var rdd1 = spark.sql("").as(encoder).toJavaRDD();
//     //     var rdd2 = spark.sql("").as(encoder2).toJavaRDD();
//     //     var rdd3 = spark.sql("").as(encoder2).toJavaRDD();
     
//     //     ds1.
//     //     // Apply zipPartitions transformation
//     //     rdd1.iterator(null, null);
        
//     //     //spark.sql("").as(encoder).foreach(f -> f.get);
//     //     return null;
//     // }

//     // public void test3() {
//     //     var encoder = Encoders.bean(AggregateClause.class);
//     //     var encoder2 = Encoders.bean(LetClause.class);
//     //     var rdd1 = spark.sql("").as(encoder).toJavaRDD();
//     //     var rdd2 = spark.sql("").as(encoder2).toJavaRDD();
//     //     var rdd3 = spark.sql("").as(encoder2).toJavaRDD();
     
//     //     // Apply zipPartitions transformation
//     //     JavaRDD<String> result = rdd1.zipPartitions(rdd2, rdd3, (iter1, iter2, iter3) -> {
//     //         @Override
//     //         public Iterator<String> call(Iterator<AggregateClause> iter1, Iterator<LetClause> iter2) throws Exception {
//     //             var output = new ArrayList<String>();

//     //             // Iterate over the elements in both partitions and perform some operation
//     //             while (iter1.hasNext() && iter2.hasNext()) {
//     //                 var num1 = iter1.next();
//     //                 var num2 = iter2.next();
//     //                 String concatenated = num1.toString() + "-" + num2.toString();
//     //                 output.add(concatenated);
//     //             }

//     //             return output.iterator();
//     //         }
//     //     });        
        
//     //     //spark.sql("").as(encoder).foreach(f -> f.get);
//     //     return null;
//     // }

//     // public void test2() {
//     //     // Initialize SparkContext
//     //     JavaSparkContext sparkContext = new JavaSparkContext("local", "ZipPartitionsExample");

//     //     // Create some example datasets
//     //     List<Integer> dataset1 = List.of(1, 2, 3, 4, 5);
//     //     List<Integer> dataset2 = List.of(6, 7, 8, 9, 10);
//     //     List<Integer> dataset3 = List.of(11, 12, 13, 14, 15);
//     //     List<Integer> dataset4 = List.of(16, 17, 18, 19, 20);
//     //     List<Integer> dataset5 = List.of(21, 22, 23, 24, 25);
//     //     List<Integer> dataset6 = List.of(26, 27, 28, 29, 30);
//     //     List<Integer> dataset7 = List.of(31, 32, 33, 34, 35);
//     //     List<Integer> dataset8 = List.of(36, 37, 38, 39, 40);
//     //     List<Integer> dataset9 = List.of(41, 42, 43, 44, 45);
//     //     List<Integer> dataset10 = List.of(46, 47, 48, 49, 50);

//     //     // Create RDDs from the datasets
//     //     JavaRDD<Integer> rdd1 = sparkContext.parallelize(dataset1);
//     //     JavaRDD<Integer> rdd2 = sparkContext.parallelize(dataset2);
//     //     JavaRDD<Integer> rdd3 = sparkContext.parallelize(dataset3);
//     //     JavaRDD<Integer> rdd4 = sparkContext.parallelize(dataset4);
//     //     JavaRDD<Integer> rdd5 = sparkContext.parallelize(dataset5);
//     //     JavaRDD<Integer> rdd6 = sparkContext.parallelize(dataset6);
//     //     JavaRDD<Integer> rdd7 = sparkContext.parallelize(dataset7);
//     //     JavaRDD<Integer> rdd8 = sparkContext.parallelize(dataset8);
//     //     JavaRDD<Integer> rdd9 = sparkContext.parallelize(dataset9);
//     //     JavaRDD<Integer> rdd10 = sparkContext.parallelize(dataset10);

//     //     // Perform zipPartitions on the RDDs
//     //     JavaRDD<String> result = rdd1.zipPartitions(rdd2, rdd3, rdd4, rdd5, rdd6, rdd7, rdd8, rdd9, rdd10,
//     //             (iter1, iter2, iter3, iter4, iter5, iter6, iter7, iter8, iter9, iter10) -> {
//     //                 StringBuilder sb = new StringBuilder();
//     //                 while (iter1.hasNext() && iter2.hasNext() && iter3.hasNext() && iter4.hasNext() && iter5.hasNext() &&
//     //                         iter6.hasNext() && iter7.hasNext() && iter8.hasNext() && iter9.hasNext() && iter10.hasNext()) {
//     //                     sb.append(iter1.next()).append("-")
//     //                             .append(iter2.next()).append("-")
//     //                             .append(iter3.next()).append("-")
//     //                             .append(iter4.next()).append("-")
//     //                             .append(iter5.next()).append("-")
//     //                             .append(iter6.next()).append("-")
//     //                             .append(iter7.next()).append("-")
//     //                             .append(iter8.next()).append("-")
//     //                             .append(iter9.next()).append("-")
//     //                             .append(iter10.next()).append("\n");
//     //                 }
//     //                 return Arrays.asList(sb.toString()).iterator();
//     //             });

//     //     // Print the result
//     //     result.collect().forEach(System.out::print);        
//     // }

//     public void cql(String cqlText) {
//         /*
//         Compilation of all relevant libraries
//         Optimization rewriting ELM is needed, ideally model agnostic
//         Bulk Retrieval for all data requirements as single dataset stream with indexes to ind retrieves
//         ForEach context e.g. patient (MapPartitioner)
//             Fast config of WindowedRetriever














// cql("")....

// compilation
// optimization
// fetch clinical data
// group by context (e.g. patient or provider)
// map -> execute
//  - retrieval
//  - terminology
//  - output
// assimilate output results

// Compilation
// Evaluation
// RetrieveOptimization / RetrievalPlan
        
//          */
//     }

//     class ParameterBuilder {
//     }

//     public static class Builder {

//         private SparkSession spark;
//         private Configuration configuration = new Configuration();
        
//         private Builder(SparkSession spark) {
//             this.spark = spark;
//         }

//         public Builder withConfig(String key, String value) {
//             configuration.write(key, value);
//             return this;
//         }

//         public Builder withConfig(Configuration configuration) {
//             this.configuration = configuration;
//             return this;
//         }

//         public SparkCqlSession create() {
//             var models = new ArrayList<ModelAdapter>();
//             var modelBuilders = LibraryAdapterFactory.providers(false);
//             modelBuilders.forEachRemaining(builder -> {
//                 models.add(builder.create(configuration));
//             });

//             var sources = new ArrayList<DataAdapter>();
//             var sourceBuilders = DataAdapterFactory.providers(false);
//             sourceBuilders.forEachRemaining(builder -> {
//                 sources.add(builder.create(configuration, spark));
//             });

//             return new SparkCqlSession(spark, models, sources);
//         }
//     }

//     public static Builder build(SparkSession spark) {
//         return new SparkCqlSession.Builder(spark);
//     }
// }