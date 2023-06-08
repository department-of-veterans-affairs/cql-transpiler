package gov.va.sparkcql.evaluator

import gov.va.sparkcql.TestBase
import collection.JavaConverters._
import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.dataprovider.{FileDataProvider, SyntheaDataProvider, PopulationSize, DataAdapter}
import gov.va.sparkcql.model.fhir.r4._
import gov.va.sparkcql.compiler.{Compiler, Compilation}
import java.io.StringWriter
import org.cqframework.cql.cql2elm.LibraryContentType
import org.cqframework.cql.elm.serializing.ElmLibraryWriterFactory
import java.io.{File, FileWriter}

class EvaluatorTest extends TestBase {

  lazy val compiler = new Compiler()

  lazy val evaluator = {
    val clinicalDataProvider = SyntheaDataProvider(PopulationSize.PopulationSize10)
    //val terminologyDataProvider = FileDataProvider("./src/test/resources/valueset")
    val clinicalDataAdapter: Option[DataAdapter] = Some(clinicalDataProvider.createAdapter(spark))
    val terminologyDataAdapter: Option[DataAdapter] = None  // builder.clinicalDataProvider.map[DataAdapter](_.createAdapter(builder.spark)).headOption

    new Evaluator(None, spark, clinicalDataAdapter, terminologyDataAdapter)
  }

  // "An Evaluator" should "should retrieve Encounter data" in {
  //   val compilation = compiler.compile(List("using QUICK \n define testEnc: [Encounter]"))
  //   val evaluation: Evaluation = evaluator.eval(compilation)
  // }

  // it should "should execute multiple retrieves" in {
  //   val compilation = compiler.compile(List("using QUICK \n define testEncounter: [Encounter] \n define testCondition: [Condition]"))
  //   val evaluation: Evaluation = evaluator.eval(compilation)
  // }

  "An EvaluatorTest" should "filter based on MeasurementPeriod parameter" in {
    
    val compilation = compiler.compile(List(
      """using QUICK
          define "testEncounter":
            ["Encounter"] E
          """
      ))
    val evaluation: Evaluation = evaluator.eval(compilation)
    showEvaluation(evaluation)
  }

  def showEvaluation(evaluation: Evaluation): Unit = {
    evaluation.libraries.map(_.statements.map(_.result.show()))
  }

  def writeCompilation(compilation: Compilation): Unit = {
    compilation.libraries.map(library => {
      val name = if (library.getIdentifier().getId() != null) { library.getIdentifier().getId() } else { java.util.UUID.randomUUID.toString } + ".json"
      val writer = new FileWriter(new File(name))
      ElmLibraryWriterFactory.getWriter(LibraryContentType.JSON.mimeType()).write(library, writer)
    })
  }
}