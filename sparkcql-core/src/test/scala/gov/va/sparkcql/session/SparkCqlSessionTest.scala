package gov.va.sparkcql.session

import gov.va.sparkcql.TestBase
import collection.JavaConverters._
import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.model.fhir.r4._
import gov.va.sparkcql.dataprovider.{FileDataProvider, SyntheaDataProvider, PopulationSize}
import gov.va.sparkcql.model.{VersionedIdentifier, DataTypeRef}

class SparkCqlSessionTest extends TestBase {

  val sparkcql = {
    SparkCqlSession.build(spark)
      .withLibraryData(FileDataProvider("./src/test/resources/cql"))
      .withClinicalData(SyntheaDataProvider(PopulationSize.PopulationSize10))
      .create()
  }

  "A SparkCqlSession" should "should not be creatable outside of builder" in {
    assertDoesNotCompile("val builder = new SparkCqlSession.Builder(null)")
    assertDoesNotCompile("val sparkcql = new SparkCqlSession(null)")
  }

  it should "support statically typed retrievals" in {
    assert(sparkcql.retrieve[Encounter]().head().status.isDefined)
    assert(sparkcql.retrieve[Condition]().head().id != null)
  }

  it should "support dynamically typed retrievals" in {
    val df = sparkcql.retrieve(DataTypeRef("http://hl7.org/fhir", "Encounter", None))
    assert(df.head.getAs[String]("status") != null)
  }

  it should "evaluate mock ED measure by identifier" in {
    sparkcql.cql(Seq(VersionedIdentifier("ED", None, Some("1.0"))))
  }

  //   val eval = sparkcql.cql[FhirEncounterData]("define TheData: [Encounter]")

  //     //.withBinding(Binding[LibraryData](FileDataProvider("./src/test/resources/cql")))
  // }
}