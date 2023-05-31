package gov.va.sparkcql.common

import gov.va.sparkcql.TestBase

class CommonTest extends TestBase {
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
}