package gov.va.sparkcql.binding

import org.scalatest.flatspec.AnyFlatSpec
import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.common.Serialization._
import gov.va.sparkcql.Sparkable
import gov.va.sparkcql.model.fhir._
import gov.va.sparkcql.model.fhir.Encounter

class BundleFolderBindingTest extends AnyFlatSpec with Sparkable {
  
  lazy val binding = {
    new BundleFolderBinding(spark, "../data/fhir/bundle")
  }

  "A BundleFolderBinding" should "retrieve Encounter data" in {
    val encounter = binding.retrieve[Encounter](Coding(system="system", code="Encounter"), None)
    assert(List("finished", "active", "completed").contains(encounter.get.take(1).head.status.get))
  }

  it should "retrieve Condition data" in {
    val condition = binding.retrieve[Condition](Coding(system="system", code="Condition", version=Some("R4")), None)
    assert(condition.get.take(1).head.id != None)
  }

  it should "filter on code equality" in {
    // TODO
  }
}