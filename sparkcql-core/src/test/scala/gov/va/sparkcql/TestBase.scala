package gov.va.sparkcql

import org.scalatest.flatspec.AnyFlatSpec
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner
import collection.JavaConverters._
import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.io.Log
import gov.va.sparkcql.translator.TranslationPack
import org.hl7.elm.{r1 => elm}
import org.cqframework.cql.elm.serializing.ElmLibraryWriterFactory
import java.io.{FileWriter, File}
import java.nio.file.{Files, Paths}

@RunWith(classOf[JUnitRunner])
abstract class TestBase extends AnyFlatSpec {

  val elmOutputFolder = "../.temp/"

  lazy val spark = {
    SparkSession.builder()
      .master("local[1]")
      .getOrCreate()
  }

  def getResource(path: String): String = {
    val stream = getClass.getResourceAsStream(path)
    scala.io.Source.fromInputStream(stream).mkString
  }

  def assertTranslation(translation: TranslationPack): Unit = {
    assert(translation.errors().length == 0, translation.errors().headOption.map(_.getMessage()))
  }

  def diagnoseTranslation(translation: TranslationPack): Unit = {
    translation.output.map(_.statements.foreach(r => Log.info(r.result)))
    writeElm(translation.output.map(_.library))
  }

  protected def writeElm(libraries: Seq[elm.Library]): Unit = {
    libraries.map(library => {
      val name = if (library.getIdentifier().getId() != null) { library.getIdentifier().getId() } else { java.util.UUID.randomUUID.toString }
      Files.createDirectories(Paths.get(elmOutputFolder))
      val writer = new FileWriter(new File(elmOutputFolder + name + ".json"))
      ElmLibraryWriterFactory.getWriter("application/elm+json").write(library, writer)
    })
  }    
}