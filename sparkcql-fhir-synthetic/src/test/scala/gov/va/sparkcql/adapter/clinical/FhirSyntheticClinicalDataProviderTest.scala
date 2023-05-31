package gov.va.sparkcql.adapter.clinical

import org.scalatest.flatspec.AnyFlatSpec
import org.apache.spark.sql.{SparkSession, Dataset, DataFrame, Row}
import org.apache.spark.sql.functions._
import org.hl7.elm.r1.Code
import gov.va.sparkcql.TestBase

class FhirSyntheticClinicalDataAdapterTest extends AnyFlatSpec with TestBase {

  lazy val provider10 = {
    new FhirSyntheticClinicalDataAdapter(FhirSyntheticClinicalDataAdapter.PopulationSize10)
  }
    
  "A FhirSyntheticClinicalDataAdapter" should "return exactly 10 bundles when using PopulationSize10" in {
    assert(
      provider10.retrieve(spark, new Code().withCode("Patient"), None).get
        .distinct.count() == 10
    )
  }

  it should "retrieve encounters when using PopulationSize10" in {
    val provider = new FhirSyntheticClinicalDataAdapter(FhirSyntheticClinicalDataAdapter.PopulationSize10)
    assert(
      provider10.retrieve(spark, new Code().withCode("Encounter"), None).get
        .count() > 100
    )
  }

  it should "should lazily load" in {
    val someNumberBetweenAllPossibleAndLoadedTypes = 5
    assert(spark.sql("SHOW TABLES").count() < someNumberBetweenAllPossibleAndLoadedTypes)
  }
}