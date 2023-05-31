package gov.va.sparkcql.translation.elm2spark

import org.scalatest.flatspec.AnyFlatSpec
import gov.va.sparkcql.dataprovider.clinical.FhirSyntheticClinicalDataProvider
import gov.va.sparkcql.TestBase

class ElmSparkTranslatorTest extends AnyFlatSpec with TestBase {

  lazy val translator: ElmSparkTranslator = {
    new ElmSparkTranslator(spark, new FhirSyntheticClinicalDataProvider(size = FhirSyntheticClinicalDataProvider.PopulationSize10), null, null)
  }

  "A ElmSparkTranslator" should "perform basic retrieves" in {
    // assert(transformer.retrieve(Code("Condition")).get.count() > 100)
    // assert(transformer.retrieve(Code("Encounter")).get.head().getAs[String]("status") == "finished")
  }

  it should "allow clients to mount their own data conforming to model standards" in {

  }
  
  it should "support QDM 5" in {
  }

  it should "allow for custom models" in {
  }
}