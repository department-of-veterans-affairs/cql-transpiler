package gov.va.sparkcql.common

import scala.concurrent.duration._

class Stopwatch {
  val start: Long = System.nanoTime()
  var end: Long = 0

  def stop() = {
    end = System.nanoTime()
  }

  def lapse: Duration = {
    if (end == 0) {
      return Duration.fromNanos(System.nanoTime() - start)
    } else {
      return Duration.fromNanos(end - start)
    }
  }

  override def toString: String = {
    val duration = lapse
    val HH = duration.toHours
    val MM = duration.toMinutes
    val SS = duration.toSeconds
    val MS = duration.toMillis
    return s"${HH}h:${MM}m:${SS}s:${MS}ms"
  }
}

object Stopwatch {
  def apply(): Stopwatch = {
    return new Stopwatch()
  }
}