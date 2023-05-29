// package gov.va.sparkcql.translation

// import org.scalatest.flatspec.AnyFlatSpec
// import gov.va.sparkcql.Testable
// import gov.va.sparkcql.dataprovider.SyntheticClinicalDataProvider

// class ElmSparkTranslatorTest extends AnyFlatSpec with Testable {

//   def bundlePath = "../data/fhir/bundle"

//   lazy val translator: ElmSparkTranslator = {
//     new ElmSparkTranslator(spark, new SyntheticClinicalDataProvider(bundlePath), null, null)
//   }

//   "A ElmSparkTranslator" should "perform basic retrieves" in {
//     //transformer.transform()
//     // assert(transformer.retrieve(Code("Condition")).get.count() > 100)
//     // assert(transformer.retrieve(Code("Encounter")).get.head().getAs[String]("status") == "finished")
//   }

//   it should "allow clients to mount their own data conforming to model standards" in {
//   }
  
//   it should "support QDM 5" in {
//   }

//   it should "allow for custom models" in {
//   }
// }