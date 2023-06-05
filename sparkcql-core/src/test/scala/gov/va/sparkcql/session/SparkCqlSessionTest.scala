package gov.va.sparkcql.compiler.cql2elm

import gov.va.sparkcql.TestBase
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertThrows
import java.io.StringWriter
import collection.JavaConverters._
import org.hl7.elm.r1.Library
import org.cqframework.cql.elm.serializing.ElmLibraryWriterFactory
import org.hl7.elm.r1.VersionedIdentifier
import gov.va.sparkcql.session.SparkCqlSession
import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.model.Binding
import gov.va.sparkcql.model.LibraryData
import gov.va.sparkcql.dataprovider.FileDataProvider

class SparkCqlSessionTest extends TestBase {

  "A SparkCqlSession" should "should not be creatable outside of builder" in {
    assertDoesNotCompile("val builder = new SparkCqlSession.Builder(null)")
    assertDoesNotCompile("val sparkCql = new SparkCqlSession(null)")
  }

  it should "bind to providers" in {
    val sparkCql = SparkCqlSession.build(spark)
      .withBinding(Binding[LibraryData](FileDataProvider("./src/test/resources/cql")))
      //.withBinding(Binding[LibraryData](FileDataProvider("./src/test/resources/cql")))
  }
}