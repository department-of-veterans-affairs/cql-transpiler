package gov.va.sparkcql.test

import scala.reflect.runtime.universe._
import collection.JavaConverters._
import gov.va.sparkcql.types._
import gov.va.sparkcql.io.Log
import gov.va.sparkcql.SparkCqlSession
import gov.va.sparkcql.adapter.library.FileLibraryAdapter
import org.hl7.elm.r1.VersionedIdentifier

class PseudoQuickMeasureTests extends IntegrationTestBase {

  val sparkcql: SparkCqlSession = {
    SparkCqlSession.build(spark)
      .withConfig("sparkcql.filelibraryadapter.path", "./src/test/resources/cql")
      .withConfig("sparkcql.syntheadataadapter.samplesize", "10")
      .create()
  }

  "A PseudoMeasureTest" should "should calculate Emergency Department" in {
    val parameter = SparkCqlSession.buildParameter("Measurement Period").dateTimeInterval("2013-01-01", "2014-01-01")
    val evaluation = sparkcql.cql(parameter, Seq(new VersionedIdentifier().withId("ED_QUICK").withVersion("1.0")))
    assertEvaluation(evaluation)
    diagnoseEvaluation(evaluation)
  }
}