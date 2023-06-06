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
import gov.va.sparkcql.model.{Binding, LibraryData}
import gov.va.sparkcql.model.fhir._
import gov.va.sparkcql.dataprovider.{FileDataProvider, SyntheaDataProvider, PopulationSize}

class SparkCqlSessionTest extends TestBase {

  val sparkCql = {
    SparkCqlSession.build(spark)
      .withBinding(Binding[LibraryData](FileDataProvider("./src/test/resources/cql")))
      .withBinding(Binding[Encounter](SyntheaDataProvider(PopulationSize.PopulationSize10)))
      .create()
  }

  "A SparkCqlSession" should "should not be creatable outside of builder" in {
    // assertDoesNotCompile("val builder = new SparkCqlSession.Builder(null)")
    // assertDoesNotCompile("val sparkCql = new SparkCqlSession(null, null)")
  }

  it should "support direct retrievals" in {
    //sparkCql.expression[Condition]("[Condition]")
    sparkCql.retrieve[Encounter]().show()
  }

  // it should "bind to providers" in {
    
  //   val eval = sparkCql.cql[FhirEncounterData]("define TheData: [Encounter]")

  //     //.withBinding(Binding[LibraryData](FileDataProvider("./src/test/resources/cql")))
  // }
}