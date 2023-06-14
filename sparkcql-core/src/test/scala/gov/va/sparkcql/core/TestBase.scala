package gov.va.sparkcql

import org.scalatest.flatspec.AnyFlatSpec
import collection.JavaConverters._
import org.apache.spark.sql.SparkSession
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner
import org.hl7.elm.r1.Library
import org.hl7.cql_annotations.r1.CqlToElmError
import gov.va.sparkcql.core.Log

@RunWith(classOf[JUnitRunner])
abstract class TestBase extends AnyFlatSpec {

  lazy val spark = {
    SparkSession.builder()
      .master("local[1]")
      .getOrCreate()
  }

  def assertNoElmErrors(libraries: Seq[Library]): Unit = {
    val errors = libraries.flatMap(_.getAnnotation().asScala).filter {
      case e: CqlToElmError => true
      case _ => false
    }
    val x = errors.length
    assert(errors.length == 0, errors.headOption.getOrElse(""))
  }  
}