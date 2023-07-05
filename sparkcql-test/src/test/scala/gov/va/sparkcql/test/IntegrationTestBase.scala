package gov.va.sparkcql.test

import org.scalatest.flatspec.AnyFlatSpec
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner
import collection.JavaConverters._
import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.io.Log
import gov.va.sparkcql.session.Evaluation
import org.hl7.elm.r1.Library
import org.cqframework.cql.elm.serializing.ElmLibraryWriterFactory
import java.io.FileWriter
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

@RunWith(classOf[JUnitRunner])
abstract class IntegrationTestBase extends AnyFlatSpec {

  val elmOutputFolder = "./.temp/"

  lazy val spark = {
    SparkSession.builder()
      .master("local[1]")
      .getOrCreate()    
  }

  def getResource(path: String): String = {
    val stream = getClass.getResourceAsStream(path)
    scala.io.Source.fromInputStream(stream).mkString
  }

  def assertEvaluation(eval: Evaluation): Unit = {
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
}