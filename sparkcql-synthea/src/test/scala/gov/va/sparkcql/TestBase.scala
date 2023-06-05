package gov.va.sparkcql

import org.scalatest.flatspec.AnyFlatSpec
import org.apache.spark.sql.SparkSession
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
abstract class TestBase extends AnyFlatSpec {

  lazy val spark = {
    SparkSession.builder()
      .master("local[*]")
      .getOrCreate()
  }
}