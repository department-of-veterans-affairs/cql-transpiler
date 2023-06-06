package gov.va.sparkcql.compiler.cql2elm

import gov.va.sparkcql.TestBase
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertThrows
import java.io.StringWriter
import collection.JavaConverters._
import org.apache.spark.sql.SparkSession
import org.hl7.elm.r1.{Library, VersionedIdentifier}
import org.cqframework.cql.elm.serializing.ElmLibraryWriterFactory
import gov.va.sparkcql.session.SparkCqlSession
import gov.va.sparkcql.model.fhir.r4._
import gov.va.sparkcql.dataprovider.{FileDataProvider, SyntheaDataProvider, PopulationSize}
import gov.va.sparkcql.model.DataTypeRef

class SparkCqlSessionTest extends TestBase {

  val sparkCql = {
    SparkCqlSession.build(spark)
      .withLibraryDataProvider(FileDataProvider("./src/test/resources/cql"))
      .withClinicalDataProvider(SyntheaDataProvider(PopulationSize.PopulationSize10))
      .create()
  }

  "A SparkCqlSession" should "should not be creatable outside of builder" in {
    assertDoesNotCompile("val builder = new SparkCqlSession.Builder(null)")
    assertDoesNotCompile("val sparkCql = new SparkCqlSession(null)")
  }

  it should "support statically typed retrievals" in {
    assert(sparkCql.retrieve[Encounter]().head().status.isDefined)
  }

  it should "support dynamically typed retrievals" in {
    val df = sparkCql.retrieve(DataTypeRef("http://hl7.org/fhir", "Encounter", None))
    assert(df.head.getAs[String]("status") != null)
  }
    
  //   val eval = sparkCql.cql[FhirEncounterData]("define TheData: [Encounter]")

  //     //.withBinding(Binding[LibraryData](FileDataProvider("./src/test/resources/cql")))
  // }
}