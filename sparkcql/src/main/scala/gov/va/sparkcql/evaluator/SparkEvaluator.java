// package gov.va.fast.evaluator;

// import org.apache.spark.sql.Dataset;
// import org.apache.spark.sql.Row;
// import org.apache.spark.sql.SparkSession;
// import org.hl7.elm.r1.VersionedIdentifier;

// public class SparkEvaluator implements Evaluator  {
//     @Override
//     public Dataset<Row>[] transform(SparkSession spark, ResourceProvider resourceProvider, VersionedIdentifier[] libraryId) {
//         var context = new EvaluationContext(spark, resourceProvider);
//         var visitor = new SparkTranslatorVisitor();
//         visitor.visitLibrary(null, null);
//         throw new UnsupportedOperationException("Unimplemented method 'transform'");

//         // var jsonElm = Resources.load(path);
//         // var readerElm = new StringReader(jsonElm);
//         // var reader = new org.cqframework.cql.elm.serializing.jaxb.ElmJsonLibraryReader();
//         // libraryItems.add(reader.read(readerElm));        
//     }
// }