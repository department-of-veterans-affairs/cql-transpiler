package gov.va.sparkcql.binding

import org.scalatest.flatspec.AnyFlatSpec
import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.common.Serialization._
import gov.va.sparkcql.Sparkable
import gov.va.sparkcql.binding.strategy.BundleFolderBinding
import gov.va.sparkcql.model.fhir.Coding

class BundleFolderBindingTest extends AnyFlatSpec with Sparkable {
  "A BundleFolderBindingStrategyTest" should "mount synthea data" in {
    val binding = new BundleFolderBinding("../data/fhir/bundle")
    val df = binding.resolve(fixture.spark, Coding("system", "Encounter"))
    df.show()
    // val sparkConfigValue =  spark.sparkContext.getConf.get("spark.cql.binding.configuration")
    // val configurationContents = Source.fromResource(sparkConfigValue).getLines().mkString
    // val strategy = new ConfigurationBindingStrategy()
    // assert(strategy.configuration.system == "http://hl7.org/fhir/fhir-types")
    // assert(strategy.configuration.resourceTypes.size > 5)
  }
}