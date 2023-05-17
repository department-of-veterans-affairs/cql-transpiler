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

  "A BundleFolderBinding" should "bind Encounter data" in {
    val encounter = binding.resolve[Encounter](Coding(system="system", code="Encounter"))
    assert(List("finished", "active", "completed").contains(encounter.take(1).head.status.get))
    val condition = binding.resolve[Condition](Coding(system="system", code="Condition", version=Some("R4")))
    assert(condition.take(1).head.id != None)
  }

  it should "bind Condition data" in {
    val encounter = binding.resolve[Encounter](Coding(system="system", code="Encounter"))
    assert(List("finished", "active", "completed").contains(encounter.take(1).head.status.get))
    val condition = binding.resolve[Condition](Coding(system="system", code="Condition", version=Some("R4")))
    assert(condition.take(1).head.id != None)
  }

  it should "allow for retrieval operations" in {
    // TODO
  }
}