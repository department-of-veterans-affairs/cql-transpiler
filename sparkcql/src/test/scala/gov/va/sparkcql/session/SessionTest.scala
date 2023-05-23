package gov.va.sparkcql.session

import org.scalatest.flatspec.AnyFlatSpec
import gov.va.sparkcql.binding._
import gov.va.sparkcql.Sparkable
import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.model.fhir.r4.ValueSet
import gov.va.sparkcql.binding.MockDataBinding
import gov.va.sparkcql.model.fhir.r4.{Condition, Encounter}
import com.google.common.io.Resources
import java.nio.charset.StandardCharsets
import org.scalatest.tagobjects.{Slow}
import gov.va.sparkcql.model.fhir.r4.CodeableConcept

class SessionTest extends AnyFlatSpec with Sparkable {

  def session: Session = {
    val url = Resources.getResource("session-configuration.json")
    Session(spark, Resources.toString(url, StandardCharsets.UTF_8))
  }

  //def bundleBinding(spark: SparkSession) = new MockDataBinding(spark, """"../data/fhir/bundle""")

  "A Session" should "retrieve bound FHIR data without CQL" in {
    assert(session.retrieve[Condition]().get.count() > 100)
    assert(session.retrieve[Encounter]().get.head().status.get == "finished")
  }

  it should "allow retrieve filtering using a strongly typed model" in {
    assert(session.retrieve[Encounter]().get.filter(f => {f.status.get == "finished"}).count() > 500)
    // assert(session.retrieve[Encounter]().get.filter(f => f.cls match {
    //   case Some(cls) => cls.coding(0).code == "EMER"
    //   case None => false
    // }).count() > 0)
  }

  it should "allow clients to mount their own data conforming to model standards" in {
  }
  
  it should "support QDM 5" in {
  }

  it should "allow for custom models" in {
  }
}