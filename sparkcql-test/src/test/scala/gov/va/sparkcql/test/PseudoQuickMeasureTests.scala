package gov.va.sparkcql.test

import collection.JavaConverters._
import gov.va.sparkcql.core.model.{CqlContent}
import gov.va.sparkcql.core.model.VersionedId
import gov.va.sparkcql.core.Log
import gov.va.sparkcql.core.session.SparkCqlSession
import gov.va.sparkcql.core.model.Evaluation
import gov.va.sparkcql.synthea.{SyntheaSourceAdapterConfig, PopulationSize}
import gov.va.sparkcql.core.adapter.source.FileSourceAdapterConfig

class PseudoQuickMeasureTests extends IntegrationTestBase {

  val sparkcql: SparkCqlSession = {
    SparkCqlSession.build(spark)
      .withConfig(FileSourceAdapterConfig("./src/test/resources/cql"))
      .withConfig(SyntheaSourceAdapterConfig(PopulationSize.PopulationSize10))
      .create()
  }

  "A PseudoMeasureTest" should "should calculate Emergency Department" in {
    val parameter = sparkcql.parameter("Measurement Period").dateTimeInterval("2013-01-01", "2014-01-01")
    val evaluation = sparkcql.cql(parameter, Seq(VersionedId("ED_QUICK", None, Some("1.0"))))
    assertEvaluation(evaluation)
    diagnoseEvaluation(evaluation)
  }
}