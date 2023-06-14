package gov.va.sparkcql.core.session

import gov.va.sparkcql.TestBase
import collection.JavaConverters._
import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.core.model.DataType
import gov.va.sparkcql.core.model.elm.VersionedIdentifier
import gov.va.sparkcql.core.adapter.source.{FileSource}
import gov.va.sparkcql.fhir.{FhirModel, FhirDataType}
import gov.va.sparkcql.synthea.{PopulationSize, SyntheaSource}
import gov.va.sparkcql.core.model.CqlContent

class SparkCqlSessionTest extends TestBase {

  val sparkcql = {
    SparkCqlSession.build(spark)
      .withSource(FileSource("./src/test/resources/cql"))
      .withSource(SyntheaSource(PopulationSize.PopulationSize10))
      .withModel(FhirModel())
      .create()
  }

  "A SparkCqlSession" should "should not be creatable outside of builder" in {
    assertDoesNotCompile("val builder = new SparkCqlSession.Builder(null)")
    assertDoesNotCompile("val sparkcql = new SparkCqlSession(null)")
  }

  it should "support statically typed retrievals" in {
    assert(sparkcql.retrieve[CqlContent].isDefined)
  }

  it should "support dynamically typed retrievals" in {
    val df = sparkcql.retrieve(FhirDataType("Encounter"))
    assert(df.get.head.getAs[String]("status") != null)
  }

  it should "evaluate mock ED measure by identifier" in {
    sparkcql.cql(Seq(VersionedIdentifier("ED", None, Some("1.0"))))
  }

  // TODO: Negotiate (fall thru) multiple adapters correctly

  //   val eval = sparkcql.cql[FhirEncounterData]("define TheData: [Encounter]")

  //     //.withBinding(Binding[LibraryData](FileDataProvider("./src/test/resources/cql")))
  // }
}