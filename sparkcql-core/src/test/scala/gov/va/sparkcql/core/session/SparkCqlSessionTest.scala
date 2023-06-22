package gov.va.sparkcql.core.session

import gov.va.sparkcql.TestBase
import collection.JavaConverters._
import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.core.model.{CqlContent}
import gov.va.sparkcql.core.Log
import gov.va.sparkcql.core.native.source.FileSourceConfiguration

class SparkCqlSessionTest extends TestBase {

  val sparkcql = {
    SparkCqlSession.build(spark)
      .withConfig(FileSourceConfiguration("./src/test/resources/cql"))
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
    // val df = sparkcql.retrieve(FhirDataType("Encounter"))
    // assert(df.get.head.getAs[String]("status") != null)
  }
}