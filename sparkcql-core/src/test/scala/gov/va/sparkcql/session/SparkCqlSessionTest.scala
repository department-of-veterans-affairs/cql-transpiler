package gov.va.sparkcql.translation.cql2elm

import gov.va.sparkcql.TestBase
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertThrows
import java.io.StringWriter
import collection.JavaConverters._
import org.hl7.elm.r1.Library
import org.cqframework.cql.elm.serializing.ElmLibraryWriterFactory
import gov.va.sparkcql.extensions._
import org.hl7.elm.r1.VersionedIdentifier
import gov.va.sparkcql.session.SparkCqlSession
import org.apache.spark.sql.SparkSession

class SparkCqlSessionTest extends TestBase {

  "A SparkCqlSession" should "translate a single basic CQL to ELM" in {

  }
}