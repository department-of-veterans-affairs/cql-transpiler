package gov.va.sparkcql.dataprovider.clinical

import gov.va.sparkcql.TestBase
import org.apache.spark.sql.{SparkSession, Dataset, DataFrame, Row}
import org.apache.spark.sql.functions._
import org.hl7.elm.r1.Code
import gov.va.sparkcql.dataprovider.SyntheaDataProvider
import gov.va.sparkcql.dataprovider.PopulationSize

class SyntheaDataProviderTest extends TestBase {
    
  "A SyntheaDataProvider" should "lazily initialize" in {
    // val provider = new SyntheaDataProvider(PopulationSize.PopulationSize10)
    // assert(
    //   provider.fetch[] .fetch(spark, new Code().withCode("Patient"), None).get
    //     .distinct.count() == 10
    // )
  }

  it should "return exactly 10 bundles when using PopulationSize10" in {

  }

  // it should "retrieve encounters when using PopulationSize10" in {
  //   val provider = new FhirSyntheticClinicalDataProvider(FhirSyntheticClinicalDataProvider.PopulationSize10)
  //   assert(
  //     provider10.retrieve(spark, new Code().withCode("Encounter"), None).get
  //       .count() > 100
  //   )
  // }

  // it should "should lazily load" in {
  //   val someNumberBetweenAllPossibleAndLoadedTypes = 5
  //   assert(spark.sql("SHOW TABLES").count() < someNumberBetweenAllPossibleAndLoadedTypes)
  // }
}