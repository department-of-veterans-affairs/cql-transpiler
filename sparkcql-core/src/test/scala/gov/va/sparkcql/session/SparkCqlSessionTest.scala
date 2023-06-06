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
import gov.va.sparkcql.dataprovider.{FileDataProvider, SyntheaDataProvider, PopulationSize}
import gov.va.sparkcql.model.TestClinicalData
import gov.va.sparkcql.model.TestEncounter

class SparkCqlSessionTest extends TestBase {

  val sparkCql = {
    SparkCqlSession.build(spark)
      .withBinding(Binding[LibraryData](FileDataProvider("./src/test/resources/cql")))
      .withBinding(Binding[TestEncounter](SyntheaDataProvider(PopulationSize.PopulationSize10)))
      .create()
  }

  "A SparkCqlSession" should "should not be creatable outside of builder" in {
    // assertDoesNotCompile("val builder = new SparkCqlSession.Builder(null)")
    // assertDoesNotCompile("val sparkCql = new SparkCqlSession(null, null)")
  }

  it should "support direct retrievals" in {
    //sparkCql.expression[Condition]("[Condition]")
    //sparkCql.retrieve(QName("uri", "Encounter"))
    val df = sparkCql.retrieve[TestEncounter]()
    println("HERE@@@@@@@@@@@@@@@@@" + df.head().status)
    println("HERE@@@@@@@@@@@@@@@@@" + df.head().id)
    //df.show()
  }

  // it should "bind to providers" in {
    
  //   val eval = sparkCql.cql[FhirEncounterData]("define TheData: [Encounter]")

  //     //.withBinding(Binding[LibraryData](FileDataProvider("./src/test/resources/cql")))
  // }
}