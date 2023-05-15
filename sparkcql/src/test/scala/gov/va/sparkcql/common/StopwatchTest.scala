package gov.va.sparkcql.common

import org.scalatest.flatspec.AnyFlatSpec

class StopwatchTest extends AnyFlatSpec {
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
    assert(watch.toString.startsWith("0h:0m:0s:1") && watch.toString.endsWith("ms"))
    assert(1 == 1)
  }
}