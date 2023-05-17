package gov.va.sparkcql.binding

import org.scalatest.flatspec.AnyFlatSpec
import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.common.Serialization._
import gov.va.sparkcql.Sparkable
import gov.va.sparkcql.model.fhir._

class BundleFolderBindingTest extends AnyFlatSpec with Sparkable {
  "A BundleFolderBindingStrategyTest" should "mount synthea data" in {
    val binding = new BundleFolderBinding(fixture.spark, "../data/fhir/bundle")
    val spark = fixture.spark
    import spark.implicits._
    
    // val dfSample = binding.resolve(Coding(system="system", code="Encounter")).select("Resource")
    // for (item <- dfSample.take(1).toSeq) {
    //   println(item.get(0))
    // }
    
    //val df1 = binding.bind(Coding(system="system", code="Encounter")).select("Resource.*")
    val df = binding.query[Encounter](Coding(system="system", code="Encounter"))
    df.show()
    //println(df.head().id)  
    //df.show()

    // val sparkConfigValue =  spark.sparkContext.getConf.get("spark.cql.binding.configuration")
    // val configurationContents = Source.fromResource(sparkConfigValue).getLines().mkString
    // val strategy = new ConfigurationBindingStrategy()
    // assert(strategy.configuration.system == "http://hl7.org/fhir/fhir-types")
    // assert(strategy.configuration.resourceTypes.size > 5)
  }
}