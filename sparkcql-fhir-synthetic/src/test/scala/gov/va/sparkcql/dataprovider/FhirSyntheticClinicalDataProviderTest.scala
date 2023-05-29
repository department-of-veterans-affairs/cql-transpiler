package gov.va.sparkcql.dataprovider

import org.scalatest.flatspec.AnyFlatSpec
import org.apache.spark.sql.{SparkSession, Dataset, DataFrame, Row}
import org.apache.spark.sql.functions._
import org.hl7.elm.r1.Code
import gov.va.sparkcql.common.Logging
import org.apache.log4j.Level

class FhirSyntheticClinicalDataProviderTest extends AnyFlatSpec {

  lazy val spark = {
    Logging.setLoggingLevel(Level.OFF)
    SparkSession.builder()
      .master("local[*]")
      .getOrCreate()
  }
    
  "A FhirSyntheticClinicalDataProvider" should "return exactly 10 bundles when using PopulationSize10" in {
    val provider = new FhirSyntheticClinicalDataProvider(PopulationSize10)
    assert(
      provider.retrieve(spark, new Code().withCode("Patient"), None).get
        .distinct.count() == 10
    )
  }

  it should "retrieve encounters when using PopulationSize10" in {
    val provider = new FhirSyntheticClinicalDataProvider(PopulationSize10)
    assert(
      provider.retrieve(spark, new Code().withCode("Encounter"), None).get
        .count() > 100
    )
  }  
}