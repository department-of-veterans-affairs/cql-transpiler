// package gov.va.sparkcql.binding

// import org.scalatest.flatspec.AnyFlatSpec
// import org.apache.spark.sql.SparkSession
// import gov.va.sparkcql.Sparkable
// import org.apache.spark.sql.sources.Filter
// import gov.va.sparkcql.model.fhir.r4._

// class MockDataBindingTest extends AnyFlatSpec with Sparkable {
  
//   lazy val binding = {
//     new MockDataBinding(spark, "../data/fhir/bundle")
//   }

//   "A MockDataBinding" should "retrieve Encounter data" in {
//     val encounter = binding.retrieve[Encounter](None)
//     assert(List("finished", "active", "completed").contains(encounter.get.take(1).head.status.get))
//   }

//   it should "retrieve Condition data" in {
//     val condition = binding.retrieve[Condition](None)
//     assert(condition.get.take(1).head.id != None)
//   }

//   it should "filter on code equality" in {
//   }
// }