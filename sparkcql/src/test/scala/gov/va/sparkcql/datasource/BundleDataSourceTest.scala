package gov.va.sparkcql.datasource

import org.scalatest.flatspec.AnyFlatSpec
import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.common.Serialization._
import gov.va.sparkcql.Sparkable

import gov.va.sparkcql.model.fhir.r4.Encounter

class BundleDataSourceTest extends AnyFlatSpec with Sparkable {
  
  "A BundleDataSource" should "retrieve Encounter data" in {
    spark
      .read
      .format("gov.va.sparkcql.datasource.BundleDataSourceDerived")
      //.option("inferSchema", false)
      //.json("")
      .load("../data/fhir/bundle")
      // // format("json").load(paths : _*)
      // .load()
  }
}