package gov.va.sparkcql.test

import org.scalatest.flatspec.AnyFlatSpec
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner
import collection.JavaConverters._
import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.core.Log
import gov.va.sparkcql.core.session.Evaluation
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
      .config("spark.foo.man.chu", "true")
      .config("spark.foo.man.shoe", false)
      .getOrCreate()
  }

  def assertEvaluation(eval: Evaluation): Unit = {
    assert(eval.errors().length == 0, eval.errors().headOption.map(_.getMessage()))
  }

  def diagnoseEvaluation(eval: Evaluation): Unit = {
    eval.output.map(_.statements.map(_.result.map(Log.info(_))))
    writeElm(eval.output.map(_.library))
  }

  private def writeElm(libraries: Seq[Library]): Unit = {
    libraries.map(library => {
      val name = if (library.getIdentifier().getId() != null) { library.getIdentifier().getId() } else { java.util.UUID.randomUUID.toString }
      Files.createDirectories(Paths.get(elmOutputFolder))
      val writer = new FileWriter(new File(elmOutputFolder + name + ".json"))
      ElmLibraryWriterFactory.getWriter("application/elm+json").write(library, writer)
    })
  }  
}