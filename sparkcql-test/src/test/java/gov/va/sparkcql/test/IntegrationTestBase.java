package gov.va.sparkcql.test;

import org.apache.spark.sql.SparkSession;

public class IntegrationTestBase {

  protected static final String OUTPUT_FOLDER = "./.temp/";

  protected SparkSession getSpark() {
    return SparkSession.builder()
      .master("local[1]")
      .getOrCreate();
  }

  /*
  protected void assertEvaluation(Evaluation eval): Unit = {
    assert(eval.errors().length == 0, eval.errors().headOption.map(_.getMessage()))
  }

  def diagnoseEvaluation(eval: Evaluation): Unit = {
    eval.output.map(_.statements.foreach(r => Log.info(r.result)))
    writeElm(eval.output.map(_.library))
  }

  protected def writeElm(libraries: Seq[Library]): Unit = {
    libraries.map(library => {
      val name = if (library.getIdentifier().getId() != null) { library.getIdentifier().getId() } else { java.util.UUID.randomUUID.toString }
      Files.createDirectories(Paths.get(elmOutputFolder))
      val writer = new FileWriter(new File(elmOutputFolder + name + ".json"))
      ElmLibraryWriterFactory.getWriter("application/elm+json").write(library, writer)
    })
  }  
  */
}