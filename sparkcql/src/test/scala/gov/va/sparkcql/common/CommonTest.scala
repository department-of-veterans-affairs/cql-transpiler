package gov.va.sparkcql.common

import org.scalatest.flatspec.AnyFlatSpec
import gov.va.sparkcql.common.Serialization._
import gov.va.sparkcql.model.fhir.Coding 

class CommonTest extends AnyFlatSpec {
  "A Stopwatch" should "calculation duration" in {
    var watch = Stopwatch()
    Thread.sleep(10)
    watch.stop()
    assert(watch.lapse.toMillis >= 10)
  }

  it should "format properly" in {
    var watch = Stopwatch()
    Thread.sleep(100)
    watch.stop()
    assert(watch.toString.startsWith("0h:0m:0s:") && watch.toString.endsWith("ms"))
    assert(1 == 1)
  }

  "Jsons" should "deserialize a string into a list of integers" in {
    assert("[1, 2, 3]".deserializeJson[List[Int]] == List(1, 2, 3))
  }

  it should "serialize a list of integers into JSON" in {
    assert(List(1, 2, 3).serializeJson() == "[1,2,3]")
  }

  it should "symmetrically serialize a FHIR coding type" in {
    val json = """{"system":"me","version":"you","code":"us"}"""
    assert(json.deserializeJson[Coding]().serializeJson() == json)
  }
}