package gov.va.sparkcql.model

import org.scalatest.flatspec.AnyFlatSpec
import gov.va.sparkcql.model.fhir.Coding

import org.json4s._
import org.json4s.native.JsonMethods._

class FhirModelTest extends AnyFlatSpec {
  "A Fhir Coding" should "should serialize from Json" in {
    implicit val formats = DefaultFormats
    val js = """ {
      "system": "http://fhir.org",
      "version": "0x20.be",
      "code": "abc",
      "display": "some definition",
      "element": [{"id": 1}, {"id": 2}]
    }"""
    var coding: Coding = parse(js).extract[Coding]
  }
}