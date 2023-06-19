package gov.va.sparkcql.core.session

import gov.va.sparkcql.core.conversion.Conversion._
import org.hl7.elm.r1.{Interval, DateTime, Date, Literal}
import java.time.{LocalDate, LocalDateTime, ZonedDateTime}

private[session] class ParameterBuilder(name: String) {
  
  def dateTimeInterval(low: String, high: String, lowClosed: Boolean = true, highClosed: Boolean = true): Map[String, Object] = {
    val lowDateTime = low.convertTo[DateTime]
    val highDateTime = high.convertTo[DateTime]

    val interval = new Interval()
      .withLow(lowDateTime)
      .withLowClosed(lowClosed)
      .withHigh(highDateTime)
      .withHighClosed(highClosed)

    Map(name -> interval)
  }

  def dateInterval(low: String, high: String, lowClosed: Boolean = true, highClosed: Boolean = true): Map[String, Object] = {
    val lowDate = convert[String, Date](low)
    val highDate = convert[String, Date](high)

    val interval = new Interval()
      .withLow(lowDate)
      .withLowClosed(lowClosed)
      .withHigh(highDate)
      .withHighClosed(highClosed)

    Map(name -> interval)
  }
}