package gov.va.sparkcql.test

import collection.JavaConverters._
import gov.va.sparkcql.core.model.{DataType, CqlContent}
import gov.va.sparkcql.core.model.VersionedId
import gov.va.sparkcql.core.adapter.source.{FileSource}
import gov.va.sparkcql.core.Log
import gov.va.sparkcql.core.session.SparkCqlSession
import gov.va.sparkcql.fhir.{FhirModel, FhirDataType}
import gov.va.sparkcql.synthea.{PopulationSize, SyntheaSource}
import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.core.model.Evaluation

class PseudoQuickMeasureTests extends IntegrationTestBase {

  val sparkcql: SparkCqlSession = {
    SparkCqlSession.build(spark)
      .withSource(FileSource("./src/test/resources/cql"))
      .withSource(SyntheaSource(PopulationSize.PopulationSize10))
      .withModel(FhirModel())
      .create()
  }

  "A PseudoMeasureTest" should "should calculate Emergency Department" in {
    val parameter = sparkcql.parameter("Measurement Period").dateTimeInterval("2013-01-01", "2014-01-01")
    val evaluation = sparkcql.cql(parameter, Seq(VersionedId("ED_QUICK", None, Some("1.0"))))
    assertEvaluation(evaluation)
    diagnoseEvaluation(evaluation)
  }
}