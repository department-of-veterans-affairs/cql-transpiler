// package gov.va.sparkcql;

// import java.io.File;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.Paths;
// import java.util.List;
// // import org.cqframework.cql.elm.serializing.ElmLibraryWriterFactory;
// // import org.hl7.elm.r1.Library;

// import org.apache.spark.sql.SparkSession;
// import org.cqframework.cql.elm.serializing.ElmLibraryWriterFactory;
// import org.hl7.elm.r1.Library;

// public class IntegrationTestBase {

//   protected static final String OUTPUT_FOLDER = "./.temp/";

//   protected SparkSession getSpark() {
//     return SparkSession.builder()
//       .master("local[1]")
//       .getOrCreate();
//   }

//   /*
//   protected void assertEvaluation(Evaluation eval): Unit = {
//     assert(eval.errors().length == 0, eval.errors().headOption.map(_.getMessage()))
//   }

//   def diagnoseEvaluation(eval: Evaluation): Unit = {
//     eval.output.map(_.statements.foreach(r => Log.info(r.result)))
//     writeElm(eval.output.map(_.library))
//   }

//   protected def writeElm(libraries: Seq[Library]): Unit = {
//     libraries.map(library => {
//       val name = if (library.getIdentifier().getId() != null) { library.getIdentifier().getId() } else { java.util.UUID.randomUUID.toString }
//       Files.createDirectories(Paths.get(elmOutputFolder))
//       val writer = new FileWriter(new File(elmOutputFolder + name + ".json"))
//       ElmLibraryWriterFactory.getWriter("application/elm+json").write(library, writer)
//     })
//   }  
//   */

//     // public static void write(List<Library> libraries, String targetPath) {
//     //     libraries.forEach(library -> {
//     //         var name = java.util.UUID.randomUUID().toString();
//     //         if (library.getIdentifier().getId() != null) {
//     //             name = library.getIdentifier().getId();
//     //         }

//     //         try {
//     //             Files.createDirectories(Paths.get(targetPath));
//     //             var writer = new FileWriter(new File(targetPath + name + ".json"));
//     //             ElmLibraryWriterFactory.getWriter("application/elm+json").write(library, writer);
//     //         } catch (IOException e) {
//     //             throw new RuntimeException(e);
//     //         }
//     //     });
//     //   }        
// }