package gov.va.sparkcql.session

import org.scalatest.flatspec.AnyFlatSpec
import gov.va.sparkcql.binding._
import gov.va.sparkcql.Sparkable
import org.apache.spark.sql.SparkSession
import gov.va.sparkcql.binding.MockDataBinder
import com.google.common.io.Resources
import java.nio.charset.StandardCharsets
import org.scalatest.tagobjects.{Slow}
import gov.va.sparkcql.model.elm.{Code}
import gov.va.sparkcql.model.elm.{VersionedIdentifier, VersionedIdentifier2}

import org.json4s._
import org.json4s.native.JsonMethods.{parse, compact}
import org.json4s.native.Serialization.{write, read}
import gov.va.sparkcql.model.elm.VersionedIdentifier2

class SessionTest extends AnyFlatSpec with Sparkable {

  def bundlePath = "../data/fhir/bundle"

  lazy val session: CqlSession = {
    CqlSession.build(spark, new MockDataBinder(bundlePath))
      .create()
  }

  "A Session" should "retrieve bound FHIR data without CQL" in {
    assert(session.retrieve(Code("Condition")).get.count() > 100)
    assert(session.retrieve(Code("Encounter")).get.head().getAs[String]("status") == "finished")
  }

  it should "allow retrieve filtering using a strongly typed model" in {
    session.retrieve(Code("Encounter")).get.selectExpr("class.code").show()
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