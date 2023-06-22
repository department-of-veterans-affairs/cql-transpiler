package gov.va.sparkcql.test

import scala.reflect.runtime.universe._
import collection.JavaConverters._
import gov.va.sparkcql.core.types._
import gov.va.sparkcql.logging.Log
import gov.va.sparkcql.core.session.{SparkCqlSession, Evaluation}
import gov.va.sparkcql.synthea.{SyntheaSourceConfiguration, PopulationSize}
import gov.va.sparkcql.core.source.FileSourceConfiguration

class PseudoQuickMeasureTests extends IntegrationTestBase {

  val sparkcql: SparkCqlSession = {
    SparkCqlSession.build(spark)
      .withConfig(FileSourceConfiguration("./src/test/resources/cql"))
      .withConfig(SyntheaSourceConfiguration(PopulationSize.PopulationSize10))
      .create()
  }

  "A PseudoMeasureTest" should "should calculate Emergency Department" in {
    val parameter = sparkcql.parameter("Measurement Period").dateTimeInterval("2013-01-01", "2014-01-01")
    val evaluation = sparkcql.cql(parameter, Seq(Identifier("ED_QUICK", None, Some("1.0"))))
    assertEvaluation(evaluation)
    diagnoseEvaluation(evaluation)
  }
}