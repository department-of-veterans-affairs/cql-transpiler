// package gov.va.fast.evaluator;

// import static org.junit.jupiter.api.Assertions.*;

// import org.junit.jupiter.api.*;
// import org.apache.spark.sql.SparkSession;

// public class SparkTransformerTest {

//     protected SparkSession getSpark() {
//         return SparkSession.builder()
//                 .master("local[1]")
//                 .config("spark.sql.warehouse.dir", "D:\\")
//                 .config("spark.sql.catalogImplementation", "in-memory")
//                 .getOrCreate();
//     }

//     // private EvaluationContext setupContext(String[] libraries) {
//     // var jsonElm = Resources.load("cql/BasicRetrieve.json");
//     // var readerElm = new StringReader(jsonElm);
//     // var reader = new
//     // org.cqframework.cql.elm.serializing.jaxb.ElmJsonLibraryReader();
//     // var library = reader.read(readerElm);
//     // var context = new EvaluationContext(getSpark(), libraryResolver, null, null,
//     // null);
//     // var transformer = new SparkEvaluator();
//     // var x = transformer.visitLibrary(library, null);
//     // }

//     @Test
//     void testLiteralDefinitions() throws Exception {

//         var mock = new MockResourceProvider(getSpark(), new String[]{"cql/BasicRetrieve.json"});

//         // var jsonElm = Resources.load("cql/BasicRetrieve.json");
//         // var readerElm = new StringReader(jsonElm);
//         // var reader = new
//         // org.cqframework.cql.elm.serializing.jaxb.ElmJsonLibraryReader();
//         // var library = reader.read(readerElm);
//         // var context = new EvaluationContext(getSpark(), libraryResolver, null, null,
//         // null);
//         // var transformer = new SparkEvaluator();
//         // var x = transformer.visitLibrary(library, null);

//         // var data = Arrays.asList("1", "2", "3");
//         // var df = getSpark().createDataset(data, Encoders.STRING()).toDF();
//         // df.show();

//         // var df = getSpark().createDataFrame(null, getClass())
//         // var rows = customer.stream()
//         // .map(c -> new CustomerToRowMapper().call(c))
//         // .collect(Collectors.toList());

//         // var df = this.getSpark().sql("SELECT 123 foo");
//         // var df2 = df.filter(col("foo").equalTo(123));
//         // System.out.println(col("foo").getClass().getName());
//         // df2.show();
//         assertEquals(23, 23);
//     }

//     @Test
//     void testBasicRetrieve() throws Exception {
//         // var jsonElm = Resources.load("cql/BasicRetrieve.json");
//         // var readerElm = new StringReader(jsonElm);
//         // var reader = new
//         // org.cqframework.cql.elm.serializing.jaxb.ElmJsonLibraryReader();
//         // var library = reader.read(readerElm);
//         // var transformer = new SparkEvaluator(getSpark(), null, null, null, null);
//         // var x = transformer.visitLibrary(library, null);
//         // var df = getSpark().createDataFrame(null, getClass())
//         // var rows = customer.stream()
//         // .map(c -> new CustomerToRowMapper().call(c))
//         // .collect(Collectors.toList());

//         // var df = this.getSpark().sql("SELECT 123 foo");
//         // var df2 = df.filter(col("foo").equalTo(123));
//         // System.out.println(col("foo").getClass().getName());
//         // df2.show();
//         assertEquals(23, 23);
//     }
// }